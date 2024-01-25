package roudi.ir.script

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import roudi.ir.Config
import roudi.ir.blockchain.Transaction
import roudi.ir.blockchain.toTransactionRequest

private val names = listOf("John", "Jack", "Bill",
    "Tim", "Sophia", "Alice",
    "Donald", "Brad", "Juliet", "Ana")

fun main(): Unit = runBlocking {
    val client = HttpClient(CIO)
    (1 until Config.NODE_COUNT).map {
        val port = Config.PRIMARY_NODE_PORT + it
        val url = "http://${Config.HOST}:$port"

        val transactionCount = (2..5).random()
        repeat(transactionCount) {
            client.post(url) {
                setBody(buildTransaction().toTransactionRequest())
            }
        }
    }
}

private fun buildTransaction() : Transaction {
    return Transaction(
        names.random(),
        names.random(),
        (1..100).random()
    )
}

