package roudi.ir.blockchain

import roudi.ir.route.response.BlockResponse
import roudi.ir.util.DateTimeUtil

data class Block(
    val previousBlockHash: Int,
    val transactions: List<Transaction> = emptyList(),
    val creationTimeAsTimestamp: Long = System.currentTimeMillis(),
) {

    val creationTime = DateTimeUtil.format(creationTimeAsTimestamp)

}

fun List<Block>.toBlockResponse(): List<BlockResponse> {
    return mapIndexed { index, block ->
        block.toBlockResponse(index)
    }
}

fun Block.toBlockResponse(index: Int): BlockResponse {
    return BlockResponse(
        index,
        transactions.toTransactionResponse(),
        creationTimeAsTimestamp,
        creationTime,
        previousBlockHash
    )
}
