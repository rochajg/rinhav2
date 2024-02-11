package dev.rochajg.domain.usecase.transaction.exception

class InvalidTransactionRequestException(
    override val message: String,
) : Exception()
