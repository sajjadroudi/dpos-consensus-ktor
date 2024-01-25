package roudi.ir

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import roudi.ir.node.Node
import roudi.ir.node.NodeInfo
import roudi.ir.plugins.*
import kotlin.random.Random

fun main() {
    runServers()
    waitForever()
}

private fun runServers() {
    val nodeInfos = (0 until Config.NODE_COUNT).map {
        val port = Config.PRIMARY_NODE_PORT + it
        val address = "http://${Config.HOST}:$port"
        val coin = Random.nextInt(Config.MIN_COIN, Config.MAX_COIN)
        NodeInfo(address, coin)
    }

    nodeInfos.forEachIndexed { index, node ->
        val port = Config.PRIMARY_NODE_PORT + index
        runServer(port, node, nodeInfos)
    }
}

private fun runServer(port: Int, self: NodeInfo, nodes: List<NodeInfo>) {
    val node = Node(self)
        .also { it.addNodes(nodes) }

    embeddedServer(
        factory = Netty,
        port = port,
        host = Config.HOST,
        module = {
            configure(node)
        }
    ).also { it.start(wait = false) }
}

private fun waitForever() {
    while(true);
}

fun Application.configure(node: Node) {
    configureSerialization()
    configureRouting(node)
}
