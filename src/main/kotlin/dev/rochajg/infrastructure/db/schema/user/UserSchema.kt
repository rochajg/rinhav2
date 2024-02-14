package dev.rochajg.infrastructure.db.schema.user

import dev.rochajg.domain.entity.user.User
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserSchema(
    @Id
    var id: Long? = null,
    var creditLimit: Int? = null,
    var balance: Int? = null,
)

fun UserSchema.toEntity() =
    User(
        id = id!!,
        limit = creditLimit!!,
        balance = balance!!,
    )

fun User.toSchema() =
    UserSchema(
        id = id,
        creditLimit = limit,
        balance = balance,
    )
