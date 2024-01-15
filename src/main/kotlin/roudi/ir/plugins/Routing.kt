package roudi.ir.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import roudi.ir.blockchain.toBlockResponse
import roudi.ir.node.Node

fun Application.configureRouting(node: Node) {
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

    }
}
