package weather.sprorowski.io

import weather.sprorowski.io.config.commonModule
import weather.sprorowski.io.config.hr.hrModule
import weather.sprorowski.io.config.plugins.configureApplication
import weather.sprorowski.io.config.plugins.configureContainer
import weather.sprorowski.io.plugins.configureHttp
import weather.sprorowski.io.presenter.plugins.configureExceptionHandler
import weather.sprorowski.io.presenter.plugins.configureLogging
import weather.sprorowski.io.presenter.plugins.configureSerialization
import io.ktor.server.config.*
import io.ktor.server.config.ConfigLoader.Companion.load
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        environment = applicationEngineEnvironment {
            config = ConfigLoader.load()

            val applicationModules = listOf(
                commonModule(config),
                hrModule(config),
                // module
            )

            module {
                configureSerialization()
                configureLogging()
                configureExceptionHandler()
                configureHttp(config)

                configureContainer(applicationModules)
                configureApplication(applicationModules)
            }
            connector {
                host = config.property("ktor.deployment.host").getString()
                port = config.property("ktor.deployment.port").getString().toInt()
            }
        },
    ).start(wait = true)
}
