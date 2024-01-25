package roudi.ir.blockchain

import roudi.ir.route.request.TransactionRequest
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

fun Transaction.toTransactionRequest() : TransactionRequest {
    return TransactionRequest(sender, receiver, amount)
}