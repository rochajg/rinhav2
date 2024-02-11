package dev.rochajg.infrastructure.exception.handler

import com.fasterxml.jackson.databind.ObjectMapper
import dev.rochajg.application.exception.ApiException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.web.router.exceptions.UnsatisfiedRouteException
import jakarta.validation.ValidationException
import mu.KotlinLogging
import org.slf4j.Logger

@Controller
class GlobalExceptionHandler(
    private val objectMapper: ObjectMapper,
) {
    private val logger: Logger = KotlinLogging.logger {}

    @Error(global = true, exception = Exception::class)
    fun genericErrorHandler(
        request: HttpRequest<*>,
        exception: Exception,
    ): HttpResponse<ApiError> =
        HttpResponse.serverError(
            ApiError(
                error = "internal_error",
                message = "Internal Server Error",
            ),
        ).also { logError(request, it, exception) }

    @Error(global = true, exception = ValidationException::class)
    fun validationErrorHandler(
        request: HttpRequest<*>,
        validationException: ValidationException,
    ): HttpResponse<ApiError> =
        HttpResponse.badRequest(
            ApiError(
                error = "validation_error",
                message = validationException.message,
            ),
        ).also { logError(request, it, validationException) }

    @Error(global = true, exception = UnsatisfiedRouteException::class)
    fun unsatisfiedRouteErrorHandler(
        request: HttpRequest<*>,
        unsatisfiedHeaderException: UnsatisfiedRouteException,
    ): HttpResponse<ApiError> =
        HttpResponse.badRequest(
            ApiError(
                error = "validation_error",
                message = unsatisfiedHeaderException.message,
            ),
        ).also { logError(request, it, unsatisfiedHeaderException) }

    @Error(global = true, exception = ApiException::class)
    fun apiExceptionHandler(
        request: HttpRequest<*>,
        apiException: ApiException,
    ): HttpResponse<ApiError> =
        HttpResponse.status<ApiError>(HttpStatus.valueOf(apiException.statusCode))
            .body(
                ApiError(
                    error = apiException.error,
                    message = apiException.message,
                    cause = apiException.cause?.message,
                ),
            ).also { logError(request, it, apiException) }

    private fun logError(
        request: HttpRequest<*>,
        response: HttpResponse<ApiError>,
        error: Throwable,
    ) {
        String()
            .plus("[request_method: ${request.method}]\n")
            .plus("[request_uri: ${request.path}]\n")
            .plus("[request_body: ${objectMapper.writeValueAsString(request.body)}]\n")
            .plus("[response_status: ${response.status.code}]\n")
            .plus("[response_body: ${objectMapper.writeValueAsString(response.body())}]\n")
            .plus("[error: ${error.message}]")
            .also { logger.error(it, error) }
    }
}
