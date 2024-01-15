package roudi.ir.blockchain

import roudi.ir.plugins.BlockChainResponse

class BlockChain {

    private val blocks = mutableListOf<Block>(buildGenesisBlock())

    val lastBlockIndex: Int
        get() = blocks.lastIndex

    val lastBlock: Block
        get() = blocks.last()

    fun addBlock(block: Block) {
        blocks += block
    }

    private fun buildGenesisBlock(): Block {
        return Block(0)
    }

    fun validateChain(): Boolean {
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