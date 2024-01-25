package roudi.ir.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DelegateResponse(
    @SerialName("address") val address: String,
    @SerialName("coin") val coin: Int,
    @SerialName("stake") val stake: Int,
    @SerialName("voteCount") val voteCount: Int,
    @SerialName("power") val power: Int
)
