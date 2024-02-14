package dev.rochajg.infrastructure.db.repository.transaction

import dev.rochajg.infrastructure.db.schema.transaction.TransactionSchema
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface TransactionRepository : CrudRepository<TransactionSchema, UUID> {
    fun findTop10ByUserIdOrderByCreatedAtDesc(userId: Long): List<TransactionSchema>
}
