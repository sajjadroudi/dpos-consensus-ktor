package roudi.ir.route.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.blockchain.Block

@Serializable
data class BlockRequest(
    @SerialName("transactions") val transactions: List<TransactionRequest>,
    @SerialName("creationTimeAsTimestamp") val creationTimeAsTimestamp: Long,
    @SerialName("previousHash") val previousHash: Int
)

fun BlockRequest.toBlock() : Block {
    return Block(
        previousHash,
        transactions.toTransaction(),
        creationTimeAsTimestamp
    )
}