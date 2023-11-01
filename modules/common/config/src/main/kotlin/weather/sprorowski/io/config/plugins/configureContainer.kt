package weather.sprorowski.io.config.plugins

import weather.sprorowski.io.config.ApplicationModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureContainer(apps: List<ApplicationModule>) {
    install(Koin) {
        apps.forEach { app -> modules(app.container) }
    }
}
