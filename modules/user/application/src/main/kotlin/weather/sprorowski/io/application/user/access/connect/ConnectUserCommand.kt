package weather.sprorowski.io.application.user.access.connect

import weather.sprorowski.io.application.Command

data class ConnectUserCommand(
    val idToken: String,
) : Command<ConnectUserCommand.Response> {
    data class Response(val accessToken: String)
}
