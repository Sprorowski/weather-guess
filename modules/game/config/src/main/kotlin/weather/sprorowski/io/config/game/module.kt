package weather.sprorowski.io.config.game

import weather.sprorowski.io.config.ApplicationModule
import io.ktor.server.config.*
import io.ktor.server.routing.*

fun gameModule(config: ApplicationConfig) = ApplicationModule(
    container = container(config),
    application = { routing { } },
)
