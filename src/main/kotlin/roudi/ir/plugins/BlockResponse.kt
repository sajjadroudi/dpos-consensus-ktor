package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockResponse(
    @SerialName("index") val index: Int,
    @SerialName("transactions") val transactions: List<TransactionResponse>,
    @SerialName("creationTimeAsTimestamp") val creationTimeAsTimestamp: Long,
    @SerialName("creationTime") val creationTime: String,
    @SerialName("previousHash") val previousHash: Int
)
