package weather.sprorowski.io.application.user.access.connect

import weather.sprorowski.io.application.CommandHandler
import weather.sprorowski.io.domain.extension.suspend.onNull
import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.Users
import weather.sprorowski.io.domain.user.access.token.UserTokenCreator
import weather.sprorowski.io.domain.user.connect.Authenticator

class ConnectUserCommandHandler(
    private val authenticator: Authenticator,
    private val users: Users,
    private val tokenCreator: UserTokenCreator,
) : CommandHandler<ConnectUserCommand, ConnectUserCommand.Response> {
    override suspend fun handle(command: ConnectUserCommand): ConnectUserCommand.Response {
        val connected = authenticator.connect(command.idToken)
        val user = users.findByEmail(connected.email).onNull { createFromProvider(connected) }
        val token = tokenCreator.createFor(user)

        return ConnectUserCommand.Response(token)
    }

    private suspend fun createFromProvider(connected: Authenticator.User): User {
        val user = User.create(connected.name, connected.email)
        users.register(user)

        return user
    }
}
