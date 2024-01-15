package roudi.ir.node

import roudi.ir.blockchain.Block
import roudi.ir.blockchain.BlockChain
import roudi.ir.blockchain.Transaction
import kotlin.random.Random

class Node(
    selfInfo: NodeInfo
) {

    private val blockChain = BlockChain()

    private val unverifiedTransactions = mutableListOf<Transaction>()

    private val nodes = mutableSetOf<NodeInfo>()
        .also { it.add(selfInfo) }

    private var delegates = listOf<NodeInfo>()

    private val selfAddress = selfInfo.address

    private val self: NodeInfo
        get() = nodes.find { it.address == selfAddress }!!

    val lastBlockChainIndex: Int
        get() = blockChain.lastBlockIndex

    private fun createNewBlock(): Block {
        val previousBlockHash = blockChain.lastBlock.hashCode()
        val block = Block(previousBlockHash, unverifiedTransactions)
        unverifiedTransactions.clear()
        return block
    }

    fun addTransaction(transaction: Transaction) {
        unverifiedTransactions += transaction
    }

    fun addNode(node: NodeInfo) {
        nodes += node
    }

    fun addNodes(list: List<NodeInfo>) {
        nodes += list
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

    fun mine(): Block {
        if(!isDelegate())
            throw IllegalStateException("This node is not delegate!")

        if(unverifiedTransactions.isEmpty())
            throw IllegalStateException("There is no transaction to include in block!")

        val block = createNewBlock()
        blockChain.addBlock(block)

        return block
    }

    private fun isDelegate(): Boolean {
        return delegates.find { it.address == selfAddress } != null
    }

    fun getBlockChain() : BlockChain {
        return blockChain
    }

    // TEMPORARY METHODS TO DEBUG

    fun getUnverifiedTransactions() : List<Transaction> {
        return unverifiedTransactions
    }

    fun getNodes() : List<NodeInfo> {
        return nodes.toList()
    }

}