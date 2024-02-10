package dev.rochajg.domain.usecase.transaction.exception

class UserNotFoundException(
    val userId: Int,
) : Exception()
