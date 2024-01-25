package roudi.ir.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StakeAmountResponse(
    @SerialName("stake") val stake: Int
)
