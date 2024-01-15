package roudi.ir.blockchain

import roudi.ir.util.DateTimeUtil

data class Block(
    val transactions: List<Transaction>,
    val creationTimeAsTimestamp: Long,
    val previousBlockHash: Int
) {

    val creationTime = DateTimeUtil.format(creationTimeAsTimestamp)

}
