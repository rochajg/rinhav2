package dev.rochajg.infrastructure.db.schema.user

import dev.rochajg.domain.entity.user.User
import dev.rochajg.infrastructure.annotation.NoArg
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
)

fun UserSchema.toEntity() =
    User(
        id = userId,
        name = name,
        limit = limit,
        balance = balance,
    )

fun User.toSchema() =
    UserSchema(
        userId = id,
        name = name,
        limit = limit,
        balance = balance,
    )
