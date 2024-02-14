package dev.rochajg.infrastructure.db.repository.user

import dev.rochajg.infrastructure.db.schema.user.UserSchema
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface UserRepository : CrudRepository<UserSchema, Long> {
    fun find(id: Long?): UserSchema?
}
