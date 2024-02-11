package dev.rochajg.infrastructure.gateway.db.user

import dev.rochajg.domain.entity.user.User
import dev.rochajg.domain.gateway.user.UserGateway
import dev.rochajg.infrastructure.db.repository.user.UserRepository
import dev.rochajg.infrastructure.db.schema.user.toEntity
import dev.rochajg.infrastructure.db.schema.user.toSchema
import jakarta.inject.Singleton

@Singleton
class UserGatewayDB(
    private val userRepository: UserRepository,
) : UserGateway {
    override fun get(id: Int?): User? = userRepository.get(id)?.toEntity()

    override fun update(user: User) {
        userRepository.update(user.toSchema())
    }
}
