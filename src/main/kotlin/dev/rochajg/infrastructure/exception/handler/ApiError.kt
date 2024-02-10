package dev.rochajg.infrastructure.exception.handler

data class ApiError(
    val error: String,
    val message: String?,
    val cause: String? = null,
)
