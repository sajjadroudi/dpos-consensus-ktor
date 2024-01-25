package roudi.ir.route.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.route.response.TransactionResponse

@Serializable
data class BlockRequest(
    @SerialName("transactions") val transactions: List<TransactionRequest>,
    @SerialName("creationTime") val creationTime: String,
    @SerialName("previousHash") val previousHash: Int
)
