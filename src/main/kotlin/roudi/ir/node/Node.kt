package roudi.ir.node

import roudi.ir.Config
import roudi.ir.blockchain.Block
import roudi.ir.blockchain.BlockChain
import roudi.ir.blockchain.Transaction

class Node(
    selfInfo: NodeInfo
) {

    private var blockChain = BlockChain()

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

    suspend fun selectDelegates(
        specifyStakeApiCall: suspend (url: String) -> Int,
        collectVoteApiCall: suspend (targetUrl: String, nodeToVoteUrl: String) -> Int
    ): List<NodeInfo> {
        specifyNodeStakes(specifyStakeApiCall)
        collectVotes(collectVoteApiCall)
        selectDelegates()
        return delegates
    }

    private suspend fun specifyNodeStakes(apiCall: suspend (url: String) -> Int) {
        nodes.associateWith { apiCall(it.address) }
            .forEach { (node, stake) ->
                node.stake = stake
            }
    }

    fun specifyStakeAmount(): Int {
        val stake = (0..self.coin).random()
        self.stake = stake
        return stake
    }

    private suspend fun collectVotes(
        collectVoteApiCall: suspend (targetUrl: String, nodeToVoteUrl: String) -> Int
    ) {
        nodes.filter { it.stake > 0 }
            .associateWith { node ->
                collectVote(node.address) { nodeAddressToVote ->
                    collectVoteApiCall(node.address, nodeAddressToVote)
                }
            }
            .forEach { node, voteCount ->
                node.voteCount = voteCount
            }
    }

    private suspend fun collectVote(
        nodeToVoteUrl: String,
        collectVoteApiCall: suspend (targetUrl: String) -> Int
    ): Int {
        return nodes.filter { it.stake > 0 }
            .map { it.address }
            .filter { it != nodeToVoteUrl }
            .map { collectVoteApiCall(it) }
            .sum()
    }

    private fun selectDelegates() {
        delegates = nodes.sortedBy { it.power }
            .reversed()
            .take(Config.DELEGATE_COUNT)
    }

    fun vote(address: String): Int {
        return (Config.MIN_VOTE..Config.MAX_VOTE).random()
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

    fun getNeighbors(): List<NodeInfo> {
        return nodes.filter { it.address != selfAddress }
    }

    fun replaceBlockChainIfNeeded(otherBlockChains: List<BlockChain>): Boolean {
        var didReplace = false
        otherBlockChains.forEach {  other ->
            if(other.size > blockChain.size && other.isValid()) {
                blockChain = other
                didReplace = true
            }
        }
        return didReplace
    }

    fun setDelegates(delegates: List<NodeInfo>) {
        delegates.forEach { delegate ->
            nodes.removeIf { it.address == delegate.address }
        }
        nodes += delegates
    }

    // TEMPORARY METHODS TO DEBUG

    fun getUnverifiedTransactions() : List<Transaction> {
        return unverifiedTransactions
    }

    fun getNodes() : List<NodeInfo> {
        return nodes.toList()
    }

}