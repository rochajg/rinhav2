package dev.rochajg.infrastructure.db.schema.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date
import java.util.UUID

@Entity
@Table(name = "transactions")
data class TransactionSchema(
    @Id
    var id: UUID = UUID.randomUUID(),
    var userId: Long? = null,
    var value: Int? = null,
    var operation: String? = null,
    var description: String? = null,
    var createdAt: Date = Date(),
)

fun TransactionSchema.toEntity() =
    Transaction(
        userId = userId!!,
        value = value!!,
        operation = operation!!,
        description = description!!,
        createdAt = createdAt,
    )

fun Transaction.toSchema() =
    TransactionSchema(
        userId = userId,
        value = value,
        operation = operation,
        description = description,
    )
