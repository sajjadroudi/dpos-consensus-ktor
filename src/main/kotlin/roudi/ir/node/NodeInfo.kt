package roudi.ir.node

import roudi.ir.route.response.DelegateResponse

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
        address,
        coin,
        stake,
        voteCount
    )
}

fun List<NodeInfo>.toDelegateResponse(): List<DelegateResponse> {
    return map { it.toDelegateResponse() }
}
