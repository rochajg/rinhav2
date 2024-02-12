package dev.rochajg.infrastructure.db.schema.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.infrastructure.annotation.NoArg
import io.micronaut.core.annotation.ReflectiveAccess
import java.util.Date

@NoArg
@ReflectiveAccess
data class TransactionSchema(
    var value: Int,
    var operation: String,
    var description: String,
    var createdAt: Date,
)

fun TransactionSchema.toEntity() =
    Transaction(
        value = value,
        operation = operation,
        description = description,
        createdAt = createdAt,
    )

fun Transaction.toSchema() =
    TransactionSchema(
        value = value,
        operation = operation,
        description = description,
        createdAt = createdAt,
    )
