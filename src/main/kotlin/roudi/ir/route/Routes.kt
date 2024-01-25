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
import roudi.ir.route.request.TransactionRequest
import roudi.ir.route.request.toTransaction
import roudi.ir.route.response.BlockChainResponse
import roudi.ir.route.response.StakeAmountResponse
import roudi.ir.route.response.toBlockChain

fun Application.handleRoute(node: Node) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        post("/transaction") {
            val body = call.receive<TransactionRequest>()
            node.addTransaction(body.toTransaction())
            call.respond(HttpStatusCode.Created, "Successfully created!")
        }

        get("/mine") {
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
            val client = HttpClient(CIO)
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
            val stake = node.specifyStakeAmount()
            call.respond(StakeAmountResponse(stake))
        }

    }
}
