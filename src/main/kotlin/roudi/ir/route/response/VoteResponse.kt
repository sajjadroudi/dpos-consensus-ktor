package roudi.ir.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    @SerialName("vote") val vote: Int
)
