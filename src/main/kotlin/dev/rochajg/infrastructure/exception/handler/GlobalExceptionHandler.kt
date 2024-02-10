package dev.rochajg.infrastructure.exception.handler

import dev.rochajg.application.exception.ApiException
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.web.router.exceptions.UnsatisfiedRouteException
import jakarta.validation.ValidationException
import mu.KotlinLogging
import org.slf4j.Logger

@Controller
class GlobalExceptionHandler {
    private val logger: Logger = KotlinLogging.logger {}

    @Error(global = true, exception = Exception::class)
    fun genericErrorHandler(exception: Exception): HttpResponse<ApiError> =
        HttpResponse.serverError(
            ApiError(
                error = "internal_error",
                message = "Internal Server Error",
            ),
        ).also { logError(exception) }

    @Error(global = true, exception = ValidationException::class)
    fun validationErrorHandler(validationException: ValidationException): HttpResponse<ApiError> =
        HttpResponse.badRequest(
            ApiError(
                error = "validation_error",
                message = validationException.message,
            ),
        ).also { logError(validationException) }

    @Error(global = true, exception = UnsatisfiedRouteException::class)
    fun unsatisfiedRouteErrorHandler(unsatisfiedHeaderException: UnsatisfiedRouteException): HttpResponse<ApiError> =
        HttpResponse.badRequest(
            ApiError(
                error = "validation_error",
                message = unsatisfiedHeaderException.message,
            ),
        ).also { logError(unsatisfiedHeaderException) }

    @Error(global = true, exception = ApiException::class)
    fun apiExceptionHandler(apiException: ApiException): HttpResponse<ApiError> =
        HttpResponse.status<ApiError>(HttpStatus.valueOf(apiException.statusCode))
            .body(
                ApiError(
                    apiException.error,
                    message = apiException.message,
                    cause = apiException.cause?.message,
                ),
            ).also { logError(apiException) }

    fun logError(error: Throwable) {
        logger.error(error.message, error)
    }
}
