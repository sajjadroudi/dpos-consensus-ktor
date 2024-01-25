package roudi.ir.route

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import roudi.ir.blockchain.toBlockResponse
import roudi.ir.node.Node
import roudi.ir.node.toDelegateResponse
import roudi.ir.node.toDelegatesRequest
import roudi.ir.route.request.*
import roudi.ir.route.response.*
import roudi.ir.util.KtorClientBuilder

fun Application.handleRoute(node: Node) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/transaction") {
            if(node.isPrimary()) {
                call.respondText(
                    status = HttpStatusCode.InternalServerError,
                    text = "The current node is the primary node so it's not going to mine anything."
                )
            } else {
                val body = call.receive<TransactionRequest>()
                node.addTransaction(body.toTransaction())
                call.respond(HttpStatusCode.Created, "Successfully created!")
            }
        }

        get("/transaction") {
            val transactions = node.getUnverifiedTransactions().toTransactionsResponse()
            call.respond(transactions)
        }

        get("/mine") {
            if(!node.isDelegate()) {
                call.respondText(
                    status = HttpStatusCode.InternalServerError,
                    text = "This node is not delegate so it can not mine."
                )
                return@get
            }

            try {
                val block = node.mine()
                val index = node.lastBlockChainIndex
                val response = block.toBlockResponse(index)
                call.respond(response)
            } catch (ex: Exception) {
                val message = ex.message ?: "Something went wrong"
                call.respondText(text = message, status = HttpStatusCode.OK)
            }
        }

        get("/chain") {
            val chain = node.getBlockChain().toBlockChainResponse()
            call.respond(chain)
        }

        get("/chain/resolve") {
            val client = KtorClientBuilder.build()
            val otherBlockChains = node.getNeighbors()
                .map {
                    async { client.get("${it.address}/chain") }
                }
                .awaitAll()
                .map { it.body<BlockChainResponse>() }
                .map { it.toBlockChain() }

            val didReplace = node.replaceBlockChainIfNeeded(otherBlockChains)
            val message = if(didReplace)
                "There was a longer valid blockchain so the current blockchain was replaced."
            else
                "There is no longer blockchain to replace."
            call.respondText(message)
        }

        get("/node/stake") {
            if(node.isPrimary()) {
                call.respondText(
                    status = HttpStatusCode.InternalServerError,
                    text = "The current node is primary node and the primary node does not participate in voting."
                )
            } else {
                val stake = node.specifyStakeAmount()
                call.respond(StakeAmountResponse(stake))
            }
        }

        post("/node/requestVote") {
            if (node.isPrimary()) {
                call.respondText(
                    status = HttpStatusCode.InternalServerError,
                    text = "The current node is primary node and the primary node does not participate in voting."
                )
            } else {
                val request = call.receive<RequestVoteRequest>()
                val vote = node.vote(request.nodeAddressToVote)
                call.respond(VoteResponse(vote))
            }
        }

        get("/delegate") {
            val delegates = node.getDelegates().toDelegateResponse()
            call.respond(delegates)
        }

        get("/delegate/select") {
            if (!node.isPrimary()) {
                call.respondText(
                    status = HttpStatusCode.InternalServerError,
                    text = "This endpoint only works for the primary node because the primary node is responsible to conduct and manage voting process."
                )
                return@get
            }

            val client = KtorClientBuilder.build()
            val delegates = node.selectDelegates(
                specifyStakeApiCall = { targetNodeUrl ->
                    client.get("$targetNodeUrl/node/stake")
                        .body<StakeAmountResponse>()
                        .stake
                },
                collectVoteApiCall = { targetUrl, nodeToVoteUrl ->
                    client
                        .post("$targetUrl/node/requestVote") {
                            setBody(RequestVoteRequest(nodeToVoteUrl))
                        }
                        .body<VoteResponse>()
                        .vote
                }
            )
            node.getNeighbors()
                .forEach {
                    client.post("${it.address}/delegate/sync") {
                        setBody(delegates.toDelegatesRequest())
                    }
                }
            call.respond(delegates.toDelegateResponse())
        }

        post("/delegate/sync") {
            val request = call.receive<DelegatesRequest>()
            node.setDelegates(request.toNodeInfos())
            call.respondText("Successfully synced")
        }

    }
}
