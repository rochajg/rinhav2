package dev.rochajg.domain.usecase.transaction.entity

import dev.rochajg.domain.type.LowerCaseNamedType

data class TransactionRequest(
    val value: Int,
    val type: TransactionType,
    val description: String,
)

enum class TransactionType : LowerCaseNamedType {
    C,
    D,
}
