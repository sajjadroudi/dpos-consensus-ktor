package roudi.ir.blockchain

class BlockChain {

    private val blocks = mutableListOf<Block>(buildGenesisBlock())

    fun addBlock(block: Block) {
        blocks += block
    }

    private fun buildGenesisBlock(): Block {
        return Block(
            transactions = emptyList(),
            0
        )
    }

}