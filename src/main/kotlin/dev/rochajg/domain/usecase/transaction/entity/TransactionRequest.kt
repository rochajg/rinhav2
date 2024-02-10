package dev.rochajg.domain.usecase.transaction.entity

data class TransactionRequest(
    val value: Int,
    val type: String,
    val description: String,
)
