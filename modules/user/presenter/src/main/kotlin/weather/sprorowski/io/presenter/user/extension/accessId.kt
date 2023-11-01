package weather.sprorowski.io.presenter.user.extension

import weather.sprorowski.io.domain.access.exception.UserUnauthorized
import weather.sprorowski.io.domain.user.User
import com.auth0.jwt.JWT
import io.ktor.server.application.*
import io.ktor.util.pipeline.*

fun PipelineContext<Unit, ApplicationCall>.accessId(): String {
    val token = call.request.headers["Authorization"] ?: throw UserUnauthorized("Missing Bearer Token")
    val jwt = JWT.decode(token.replace("Bearer ", ""))

    return jwt.claims["id"]?.asString() ?: throw User.NotFound()
}
