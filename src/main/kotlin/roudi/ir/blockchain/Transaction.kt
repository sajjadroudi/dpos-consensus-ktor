package roudi.ir.blockchain

import roudi.ir.route.response.TransactionResponse

data class Transaction(
    val sender: String,
    val receiver: String,
    val amount: Int
)

fun List<Transaction>.toTransactionResponse(): List<TransactionResponse> {
    return map { it.toTransactionResponse() }
}

fun Transaction.toTransactionResponse(): TransactionResponse {
    return TransactionResponse(sender, receiver, amount)
}
