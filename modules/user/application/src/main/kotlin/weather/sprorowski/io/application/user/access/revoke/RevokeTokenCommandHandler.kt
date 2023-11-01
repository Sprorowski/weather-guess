package weather.sprorowski.io.application.user.access.revoke

import weather.sprorowski.io.application.CommandHandler
import weather.sprorowski.io.domain.user.access.UserAccesses

class RevokeTokenCommandHandler(
    private val userAccesses: UserAccesses,
) : CommandHandler<RevokeTokenCommand, Unit> {
    override suspend fun handle(command: RevokeTokenCommand) {
        val access = userAccesses.loadNonRevoked(command.accessId)
        access.revoke()

        userAccesses.revoke(access)
    }
}
