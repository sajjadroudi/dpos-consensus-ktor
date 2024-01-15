package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
    @SerialName("from") val sender: String,
    @SerialName("to") val receiver: String,
    @SerialName("amount") val amount: Int
)
