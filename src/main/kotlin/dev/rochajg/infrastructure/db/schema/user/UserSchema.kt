package dev.rochajg.infrastructure.db.schema.user

import dev.rochajg.domain.entity.transaction.Transaction
import dev.rochajg.domain.entity.user.User
import dev.rochajg.infrastructure.annotation.NoArg
import dev.rochajg.infrastructure.db.schema.transaction.TransactionSchema
import dev.rochajg.infrastructure.db.schema.transaction.toEntity
import dev.rochajg.infrastructure.db.schema.transaction.toSchema
import io.micronaut.core.annotation.ReflectiveAccess
import org.bson.types.ObjectId

@NoArg
@ReflectiveAccess
data class UserSchema(
    var id: ObjectId? = ObjectId(),
    var userId: Int,
    var name: String,
    var limit: Int,
    var balance: Int,
    var transactions: List<TransactionSchema> = emptyList(),
)

fun UserSchema.toEntity() =
    User(
        id = userId,
        name = name,
        limit = limit,
        balance = balance,
        transactions = transactions.map(TransactionSchema::toEntity),
    )

fun User.toSchema() =
    UserSchema(
        userId = id,
        name = name,
        limit = limit,
        balance = balance,
        transactions = transactions.map(Transaction::toSchema),
    )
