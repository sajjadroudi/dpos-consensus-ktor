package roudi.ir.plugins

import io.ktor.server.application.*
import roudi.ir.node.Node
import roudi.ir.route.handleRoute

fun Application.configureRouting(node: Node) {
    this.handleRoute(node)
}