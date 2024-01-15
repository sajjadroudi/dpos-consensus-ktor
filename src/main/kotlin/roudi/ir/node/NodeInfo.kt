package roudi.ir.node

data class NodeInfo(
    val address: String,
    private val stake: Int
) {

    var voteCount = 0

    val power = stake * voteCount
}
