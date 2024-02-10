package dev.rochajg.domain.gateway.transaction

import dev.rochajg.domain.entity.transaction.Transaction

interface TransactionGateway {
    fun create(transaction: Transaction)

    fun getByUser(userId: Int): List<Transaction>
}
