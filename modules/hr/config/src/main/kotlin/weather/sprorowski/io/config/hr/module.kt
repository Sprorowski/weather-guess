package weather.sprorowski.io.config.hr

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.presenter.hr.hrRoutes
import io.ktor.server.config.*
import io.ktor.server.routing.*

fun hrModule(config: ApplicationConfig) = ApplicationModule(
    container = container(config),
    application = { routing { hrRoutes() } },
)
