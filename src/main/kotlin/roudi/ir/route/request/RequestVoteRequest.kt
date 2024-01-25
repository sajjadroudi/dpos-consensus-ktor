package roudi.ir.route.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestVoteRequest(
    @SerialName("nodeAddressToVote") val nodeAddressToVote: String
)
