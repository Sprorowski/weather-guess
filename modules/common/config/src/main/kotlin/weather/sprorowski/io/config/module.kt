package weather.sprorowski.io.config

import io.ktor.server.config.*

fun commonModule(config: ApplicationConfig) = ApplicationModule(
    container = container(config),
    application = { },
)
