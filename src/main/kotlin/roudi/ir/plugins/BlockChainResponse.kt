package roudi.ir.plugins

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlockChainResponse(
    @SerialName("blocks") val blocks: List<BlockResponse>,
    @SerialName("size") val size: Int
)
