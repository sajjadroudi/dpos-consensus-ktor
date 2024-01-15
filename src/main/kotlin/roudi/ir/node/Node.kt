package roudi.ir.node

import roudi.ir.blockchain.Block
import roudi.ir.blockchain.BlockChain
import roudi.ir.blockchain.Transaction

class Node {

    private val blockChain = BlockChain()

    private val unverifiedTransactions = mutableListOf<Transaction>()

    private val nodes = mutableSetOf<NodeInfo>()

    private val delegates = mutableSetOf<NodeInfo>()

    fun createNewBlock() {
        val previousBlockHash = blockChain.lastBlock.hashCode()
        val block = Block(previousBlockHash, unverifiedTransactions)
        unverifiedTransactions.clear()

        blockChain.addBlock(block)
    }

}