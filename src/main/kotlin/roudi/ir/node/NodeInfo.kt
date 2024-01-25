package roudi.ir.node

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
