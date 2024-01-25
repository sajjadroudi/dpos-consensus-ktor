package roudi.ir.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.node.NodeInfo

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

fun DelegatesRequest.toNodeInfos() : List<NodeInfo> {
    return delegates.map { it.toNodeInfo() }
}

fun DelegateRequest.toNodeInfo() : NodeInfo {
    return NodeInfo(
        address = address,
        coin = coin
    ).also {
        it.stake = stake
        it.voteCount = vote
    }
}