package dev.rochajg.infrastructure.db.schema.transaction

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.infrastructure.annotation.NoArg
import io.micronaut.core.annotation.ReflectiveAccess
import org.bson.types.ObjectId
import java.util.Date

@NoArg
@ReflectiveAccess
data class TransactionSchema(
    var id: ObjectId? = ObjectId(),
    var userId: Int,
    var value: Int,
    var operation: String,
    var description: String,
    var createdAt: Date = Date(),
)

fun TransactionSchema.toEntity() =
    Transaction(
        userId = userId,
        value = value,
        operation = operation,
        description = description,
        createdAt = createdAt,
    )

fun Transaction.toSchema() =
    TransactionSchema(
        userId = userId,
        value = value,
        operation = operation,
        description = description,
    )
