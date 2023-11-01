package weather.sprorowski.io.application.user.access.refresh

import weather.sprorowski.io.application.CommandHandler
import weather.sprorowski.io.domain.user.Users
import weather.sprorowski.io.domain.user.access.token.UserTokenCreator

class RefreshTokenCommandHandler(
    private val users: Users,
    private val userTokenCreator: UserTokenCreator,
) : CommandHandler<RefreshTokenCommand, RefreshTokenCommand.Response> {
    override suspend fun handle(command: RefreshTokenCommand): RefreshTokenCommand.Response {
        val user = users.loadById(command.userId)
        val encoded = userTokenCreator.createFor(user)

        return RefreshTokenCommand.Response(encoded)
    }
}
