package weather.sprorowski.io.application.user.access.revoke

import weather.sprorowski.io.application.Command

data class RevokeTokenCommand(val accessId: String) : Command<Unit>
