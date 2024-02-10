package dev.rochajg.domain.entity.transaction

import java.util.Date

data class Transaction(
    val userId: Int,
    val value: Int,
    val operation: String,
    val description: String,
    val createdAt: Date = Date(),
)
