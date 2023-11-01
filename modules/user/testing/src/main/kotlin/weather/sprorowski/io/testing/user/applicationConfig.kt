package weather.sprorowski.io.testing.user

import io.ktor.server.config.MapApplicationConfig

val applicationConfig = MapApplicationConfig(
    "security.jwt.audience" to "http://localhost",
    "security.jwt.issuer" to "localhost",
    "security.jwt.secret" to "aaa-bbb-ccc",
    "google.oauth.clientId" to "google-client-id",
)
