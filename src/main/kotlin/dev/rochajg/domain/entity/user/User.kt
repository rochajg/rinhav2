package dev.rochajg.domain.entity.user

data class User(
    val id: Int,
    val name: String,
    val limit: Int,
    val balance: Int,
)
