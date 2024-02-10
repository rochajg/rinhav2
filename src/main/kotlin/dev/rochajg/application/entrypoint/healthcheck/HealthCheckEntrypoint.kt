package dev.rochajg.application.entrypoint.healthcheck

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HealthCheckEntrypoint {
    @Get("/ping")
    fun healthCheck(): String = "OK"
}
