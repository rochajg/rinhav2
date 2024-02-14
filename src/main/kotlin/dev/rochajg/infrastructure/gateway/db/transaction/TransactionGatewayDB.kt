package dev.rochajg.infrastructure.gateway.db.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.domain.gateway.transaction.TransactionGateway
import dev.rochajg.infrastructure.db.repository.transaction.TransactionRepository
import dev.rochajg.infrastructure.db.schema.transaction.TransactionSchema
import dev.rochajg.infrastructure.db.schema.transaction.toEntity
import dev.rochajg.infrastructure.db.schema.transaction.toSchema
import jakarta.inject.Singleton

@Singleton
class TransactionGatewayDB(
    private val transactionRepository: TransactionRepository,
) : TransactionGateway {
    override fun create(transaction: Transaction) {
        transactionRepository.save(transaction.toSchema())
    }

    override fun getByUser(userId: Long): List<Transaction> =
        transactionRepository.findTop10ByUserIdOrderByCreatedAtDesc(userId)
            .map(TransactionSchema::toEntity)
}
