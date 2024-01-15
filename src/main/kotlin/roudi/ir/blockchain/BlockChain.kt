package roudi.ir.blockchain

class BlockChain {

    private val blocks = mutableListOf<Block>(buildGenesisBlock())

    val lastBlock: Block
        get() = blocks.last()

    fun addBlock(block: Block) {
        blocks += block
    }

    private fun buildGenesisBlock(): Block {
        return Block(0)
    }

}