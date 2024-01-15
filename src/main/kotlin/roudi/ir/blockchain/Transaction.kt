package roudi.ir.blockchain

data class Transaction(
    val sender: String,
    val receiver: String,
    val amount: Int
)
