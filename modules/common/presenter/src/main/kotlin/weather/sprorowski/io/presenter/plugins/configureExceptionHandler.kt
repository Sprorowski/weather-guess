package weather.sprorowski.io.presenter.plugins

import weather.sprorowski.io.domain.access.exception.UserOperationNotAllowed
import weather.sprorowski.io.domain.access.exception.UserUnauthorized
import weather.sprorowski.io.domain.exception.*
import weather.sprorowski.io.presenter.http.response.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import org.slf4j.Logger

fun Application.configureExceptionHandler() {
    val logger: Logger by inject()
    val config by lazy { ConfigFactory.load() }
    val debug = config.tryGetString("ktor.debug") == "true"

    fun getStackTraceList(stackTrace: Array<StackTraceElement>): List<String> = stackTrace.map { it.toString() }

    fun debugException(exception: Throwable): Map<String, Any?> {
        return mapOf(
            "message" to exception.message,
            "trace" to getStackTraceList(exception.stackTrace),
            "cause" to exception.cause?.let { debugException(it) },
        )
    }

    fun debugResponse(exception: Throwable, error: ExceptionResponse): Map<String, Any?> {
        return mapOf(
            "code" to when (error) {
                is NotFoundExceptionResponse -> error.code
                else -> null
            },
            "message" to error.message,
            "details" to error.details,
            "_debug" to debugException(exception),
        ).filter { it.value != null }
    }

    suspend fun respond(call: ApplicationCall, exception: Throwable, error: ExceptionResponse) = when {
        debug -> call.respond(status = error.statusCode, message = debugResponse(exception, error))
        else -> call.respond(error.statusCode, error)
    }

    install(StatusPages) {
        // 4xx - Client Errors
        exception<InvalidRequest> { call, cause ->
            respond(call, cause, BadRequestExceptionResponse(cause.message!!))
        }
        exception<UserUnauthorized> { call, cause ->
            respond(call, cause, UnauthorizedExceptionResponse(cause.message!!))
        }
        exception<UserOperationNotAllowed> { call, cause ->
            respond(call, cause, ForbiddenExceptionResponse())
        }
        exception<EntityNotFoundException> { call, cause ->
            respond(call, cause, NotFoundExceptionResponse(cause.code, cause.message!!, cause.details))
        }
        exception<BusinessRuleException> { call, cause ->
            respond(call, cause, UnprocessableEntityExceptionResponse(cause))
        }

        // 5xx - Server Errors
        exception<NotImplementedException> { call, cause ->
            logger.error(cause.message, cause)
            respond(call, cause, NotImplementedExceptionResponse(cause.message!!))
        }
        exception<BadGatewayException> { call, cause ->
            logger.error(cause.message, cause)
            respond(call, cause, BadGatewayExceptionResponse())
        }
        exception<Throwable> { call, cause ->
            logger.error(cause.message, cause)
            respond(call, cause, InternalServerErrorExceptionResponse())
        }
    }
}
