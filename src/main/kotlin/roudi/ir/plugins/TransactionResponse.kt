package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.blockchain.Transaction

@Serializable
data class TransactionResponse(
    @SerialName("from") val sender: String,
    @SerialName("to") val receiver: String,
    @SerialName("amount") val amount: Int
)

fun List<TransactionResponse>.toTransaction(): List<Transaction> {
    return map { it.toTransaction() }
}

fun TransactionResponse.toTransaction(): Transaction {
    return Transaction(sender, receiver, amount)
}