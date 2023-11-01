package weather.sprorowski.io.presenter.user

import weather.sprorowski.io.application.user.access.connect.ConnectUserCommand
import weather.sprorowski.io.application.user.access.connect.ConnectUserCommandHandler
import weather.sprorowski.io.application.user.access.refresh.RefreshTokenCommand
import weather.sprorowski.io.application.user.access.refresh.RefreshTokenCommandHandler
import weather.sprorowski.io.application.user.access.revoke.RevokeTokenCommand
import weather.sprorowski.io.application.user.access.revoke.RevokeTokenCommandHandler
import weather.sprorowski.io.presenter.user.extension.accessId
import weather.sprorowski.io.presenter.user.extension.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val connect: ConnectUserCommandHandler by inject()
    val refresh: RefreshTokenCommandHandler by inject()
    val revoke: RevokeTokenCommandHandler by inject()

    route("/users") {
        route("/access") {
            post("/connect") {
                val command = call.receive<ConnectUserCommand>()
                val auth = connect.handle(command)

                call.respond(status = HttpStatusCode.OK, auth)
            }

            post("/refresh") {
                val command = RefreshTokenCommand(userId())
                val auth = refresh.handle(command)

                call.respond(status = HttpStatusCode.OK, auth)
            }

            post("/revoke") {
                val command = RevokeTokenCommand(accessId())
                val auth = revoke.handle(command)

                call.respond(status = HttpStatusCode.NoContent, auth)
            }
        }
    }
}
