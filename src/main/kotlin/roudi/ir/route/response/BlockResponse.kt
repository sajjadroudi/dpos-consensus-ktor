package roudi.ir.route.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.blockchain.Block
import roudi.ir.route.request.TransactionResponse
import roudi.ir.route.request.toTransaction

@Serializable
data class BlockResponse(
    @SerialName("index") val index: Int,
    @SerialName("transactions") val transactions: List<TransactionResponse>,
    @SerialName("creationTimeAsTimestamp") val creationTimeAsTimestamp: Long,
    @SerialName("creationTime") val creationTime: String,
    @SerialName("previousHash") val previousHash: Int
)

fun List<BlockResponse>.toBlock(): List<Block> {
    return map { it.toBlock() }
}

fun BlockResponse.toBlock(): Block {
    return Block(previousHash, transactions.toTransaction(), creationTimeAsTimestamp)
}
