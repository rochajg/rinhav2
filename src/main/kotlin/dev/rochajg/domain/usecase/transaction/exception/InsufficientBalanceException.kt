package dev.rochajg.domain.usecase.transaction.exception

class InsufficientBalanceException(
    val userId: Long,
    val actualBalance: Int,
) : Exception()
