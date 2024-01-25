package roudi.ir.node

import roudi.ir.route.request.DelegateRequest
import roudi.ir.route.response.DelegateResponse
import roudi.ir.route.request.DelegatesRequest

data class NodeInfo(
    val address: String,
    val coin: Int
) {

    var voteCount = 0

    var stake: Int = 0
        set(value) {
            if(value < coin)
                field = value
        }

    val power: Int
        get() = voteCount * stake
}

fun NodeInfo.toDelegateResponse(): DelegateResponse {
    return DelegateResponse(
        address = address,
        coin = coin,
        stake = stake,
        voteCount = voteCount,
        power = power
    )
}

fun List<NodeInfo>.toDelegateResponse(): List<DelegateResponse> {
    return map { it.toDelegateResponse() }
}

fun List<NodeInfo>.toDelegatesRequest() : DelegatesRequest {
    return DelegatesRequest(map { it.toDelegateRequest() })
}

private fun NodeInfo.toDelegateRequest() : DelegateRequest {
    return DelegateRequest(
        address = address,
        coin = coin,
        stake = stake,
        vote = voteCount
    )
}