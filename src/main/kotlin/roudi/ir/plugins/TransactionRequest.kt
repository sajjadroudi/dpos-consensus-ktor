package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequest(
    @SerialName("sender") val sender: String,
    @SerialName("receiver") val receiver: String,
    @SerialName("amount") val amount: Int
)
