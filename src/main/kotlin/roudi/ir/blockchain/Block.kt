package roudi.ir.blockchain

import roudi.ir.util.DateTimeUtil

data class Block(
    val transactions: List<Transaction>,
    val previousBlockHash: Int,
    val creationTimeAsTimestamp: Long = System.currentTimeMillis(),
) {

    val creationTime = DateTimeUtil.format(creationTimeAsTimestamp)

}
