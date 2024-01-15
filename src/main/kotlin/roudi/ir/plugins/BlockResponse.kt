package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockResponse(
    @SerialName("index") val index: Int,
    @SerialName("transactions") val transactions: List<TransactionResponse>,
    @SerialName("previous_hash") val previousHash: Int
)
