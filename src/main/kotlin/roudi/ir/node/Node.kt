package roudi.ir.node

import roudi.ir.blockchain.Block
import roudi.ir.blockchain.BlockChain
import roudi.ir.blockchain.Transaction
import kotlin.random.Random

class Node {

    private val blockChain = BlockChain()

    private val unverifiedTransactions = mutableListOf<Transaction>()

    private val nodes = mutableSetOf<NodeInfo>()

    private var delegates = listOf<NodeInfo>()

    fun createNewBlock() {
        val previousBlockHash = blockChain.lastBlock.hashCode()
        val block = Block(previousBlockHash, unverifiedTransactions)
        unverifiedTransactions.clear()

        blockChain.addBlock(block)
    }

    fun addTransaction(transaction: Transaction) {
        unverifiedTransactions += transaction
    }

    fun addNode(node: NodeInfo) {
        nodes += node
    }

    fun doVotingProcess() {
        simulateVoting()
    }

    private fun simulateVoting() {
        nodes.forEach { node ->
            val vote = Random.nextInt(0, 100)
            node.voteCount = vote
        }
    }

    fun selectDelegates() {
        delegates = nodes.sortedBy { it.voteCount }
            .reversed()
            .take(3)
    }

}