package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import roudi.ir.blockchain.BlockChain

@Serializable
data class BlockChainResponse(
    @SerialName("blocks") val blocks: List<BlockResponse>,
    @SerialName("size") val size: Int
)

fun BlockChainResponse.toBlockChain(): BlockChain {
    return BlockChain(blocks.toBlock())
}