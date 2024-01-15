package roudi.ir.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import roudi.ir.node.Node

fun Application.configureRouting(node: Node) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
