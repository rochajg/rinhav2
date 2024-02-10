package dev.rochajg.application.exception

class ApiException(
    val error: String = "internal_error",
    val statusCode: Int = 500,
    override val message: String? = "Internal Server Error",
    override val cause: Throwable?,
) : RuntimeException(message, cause)
