package weather.sprorowski.io.application.user.access.refresh

import weather.sprorowski.io.application.Command

data class RefreshTokenCommand(val userId: String) : Command<RefreshTokenCommand.Response> {
    data class Response(val accessToken: String)
}
