package roudi.ir.node

data class NodeInfo(
    val address: String,
    private val stake: Int,
    private val voteCount: Int
) {
    val power = stake * voteCount
}
