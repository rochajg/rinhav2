package dev.rochajg.domain.gateway.user

import dev.rochajg.domain.entity.user.User

interface UserGateway {
    fun get(id: Long?): User?

    fun update(user: User)
}
