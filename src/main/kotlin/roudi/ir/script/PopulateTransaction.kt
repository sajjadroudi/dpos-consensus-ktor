package roudi.ir.script

import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import roudi.ir.Config
import roudi.ir.blockchain.Transaction
import roudi.ir.blockchain.toTransactionRequest
import roudi.ir.util.KtorClientBuilder

private val names = listOf(
    "John", "Jack", "Bill",
    "Tim", "Sophia", "Alice",
    "Donald", "Brad", "Juliet", "Ana",
    "Jin", "Pam", "Michele", "Kevin",
    "Sam", "Mosh", "Edvin", "Wayne", "Gary",
    "Emmy", "Scarlett", "Lana", "James", "Adele"
)

fun main(): Unit = runBlocking {
    val client = KtorClientBuilder.build()
    (1 until Config.NODE_COUNT).map {
        val port = Config.PRIMARY_NODE_PORT + it
        val url = "http://${Config.HOST}:$port/transaction"

        val transactionCount = (2..5).random()
        repeat(transactionCount) {
            val response = client.post(url) {
                val request = buildTransaction().toTransactionRequest()
                setBody(request)
            }
        }
    }
}

private fun buildTransaction(): Transaction {
    return Transaction(
        names.random(),
        names.random(),
        (1..100).random()
    )
}

