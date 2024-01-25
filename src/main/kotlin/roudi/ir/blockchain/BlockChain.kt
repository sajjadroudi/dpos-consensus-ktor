package roudi.ir.blockchain

import roudi.ir.route.response.BlockChainResponse

class BlockChain(
    initialBlocks: List<Block> = emptyList()
) {

    private val blocks = initialBlocks.ifEmpty {
        listOf(buildGenesisBlock())
    }.toMutableList()

    val lastBlockIndex: Int
        get() = blocks.lastIndex

    val lastBlock: Block
        get() = blocks.last()

    val size: Int
        get() = blocks.size

    fun addBlock(block: Block) {
        blocks += block
    }

    private fun buildGenesisBlock(): Block {
        return Block(0, emptyList(), 0)
    }

    fun isValid(): Boolean {
        var index = 1

        while(index < blocks.size) {
            val previousBlockHash = blocks[index].previousBlockHash
            val previousBlock = blocks[index - 1]

            if(previousBlockHash != previousBlock.hashCode())
                return false

            index++
        }

        return true
    }

    fun toBlockChainResponse(): BlockChainResponse {
        return BlockChainResponse(blocks.toBlockResponse(), blocks.size)
    }

}