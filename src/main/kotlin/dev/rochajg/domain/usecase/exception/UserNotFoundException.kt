package dev.rochajg.domain.usecase.exception

class UserNotFoundException(
    val userId: Long?,
) : Exception()
