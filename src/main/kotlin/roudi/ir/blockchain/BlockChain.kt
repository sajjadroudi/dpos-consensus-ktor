package roudi.ir.blockchain

class BlockChain {

    private val blocks = mutableListOf<Block>()

    fun addBlock(block: Block) {
        blocks += block
    }

}