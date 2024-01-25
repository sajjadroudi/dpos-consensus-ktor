package roudi.ir.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DelegatesRequest(
    @SerialName("delegates") val delegates: List<DelegateRequest>
)

@Serializable
data class DelegateRequest(
    @SerialName("address") val address: String,
    @SerialName("coin") val coin: Int,
    @SerialName("stake") val stake: Int,
    @SerialName("vote") val vote: Int
)
