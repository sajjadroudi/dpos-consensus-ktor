package roudi.ir

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import roudi.ir.plugins.*

fun main() {
    runServers()
}

private fun runServers() {
    repeat(Config.NODE_COUNT) {
        runServer(Config.PRIMARY_NODE_PORT + it)
    }
}

private fun runServer(port: Int) {
    embeddedServer(
        factory = Netty,
        port = port,
        host = Config.HOST,
        module = Application::module
    ).also { it.start(wait = false) }
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
