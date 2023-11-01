package weather.sprorowski.io.config.user

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.presenter.user.userRoutes
import io.ktor.server.config.*
import io.ktor.server.routing.*

fun userModule(config: ApplicationConfig) = ApplicationModule(
    container = container(config),
    application = {
        routing { userRoutes() }
    },
)
