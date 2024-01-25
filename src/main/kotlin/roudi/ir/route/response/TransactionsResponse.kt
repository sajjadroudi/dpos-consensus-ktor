package roudi.ir.route.response

import kotlinx.serialization.Serializable
import roudi.ir.blockchain.Transaction
import roudi.ir.blockchain.toTransactionResponse

@Serializable
data class TransactionsResponse(
    val transactions: List<TransactionResponse>
)

fun List<Transaction>.toTransactionsResponse(): TransactionsResponse {
    return TransactionsResponse(this.toTransactionResponse())
}
