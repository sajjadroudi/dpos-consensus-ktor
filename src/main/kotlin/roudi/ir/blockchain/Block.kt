package roudi.ir.blockchain

import roudi.ir.plugins.BlockResponse
import roudi.ir.util.DateTimeUtil

data class Block(
    val previousBlockHash: Int,
    val transactions: List<Transaction> = emptyList(),
    val creationTimeAsTimestamp: Long = System.currentTimeMillis(),
) {

    val creationTime = DateTimeUtil.format(creationTimeAsTimestamp)

}

fun Block.toBlockResponse(index: Int): BlockResponse {
    return BlockResponse(
        index,
        transactions.toTransactionResponse(),
        previousBlockHash
    )
}
