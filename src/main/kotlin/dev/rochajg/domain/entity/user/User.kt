package dev.rochajg.domain.entity.user

import dev.rochajg.domain.entity.transaction.Transaction

data class User(
    val id: Int,
    val name: String,
    val limit: Int,
    val balance: Int,
    val transactions: List<Transaction> = emptyList(),
)
