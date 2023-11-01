package weather.sprorowski.io.config

import io.ktor.server.application.Application
import org.koin.core.module.Module

class ApplicationModule(
    val container: Module,
    val application: Application.() -> Unit = {},
)
