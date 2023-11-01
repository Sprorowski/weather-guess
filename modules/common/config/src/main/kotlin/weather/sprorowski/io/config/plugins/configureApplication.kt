package weather.sprorowski.io.config.plugins

import weather.sprorowski.io.config.ApplicationModule
import io.ktor.server.application.Application

fun Application.configureApplication(apps: List<ApplicationModule>) {
    apps.forEach { module -> module.application.invoke(this) }
}
