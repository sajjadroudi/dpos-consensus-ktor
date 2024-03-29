package roudi.ir.route.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.blockchain.Transaction

@Serializable
data class TransactionRequest(
    @SerialName("from") val sender: String,
    @SerialName("to") val receiver: String,
    @SerialName("amount") val amount: Int
)

fun List<TransactionRequest>.toTransaction(): List<Transaction> {
    return map { it.toTransaction() }
}

fun TransactionRequest.toTransaction() = Transaction(
    sender,
    receiver,
    amount
)