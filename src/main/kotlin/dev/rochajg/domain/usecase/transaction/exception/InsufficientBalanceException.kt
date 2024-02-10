package dev.rochajg.domain.usecase.transaction.exception

class InsufficientBalanceException(
    val userId: Int,
) : Exception()
