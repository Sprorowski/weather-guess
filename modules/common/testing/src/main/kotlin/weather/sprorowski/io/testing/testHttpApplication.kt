package weather.sprorowski.io.testing

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.presenter.plugins.configureExceptionHandler
import weather.sprorowski.io.presenter.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.koin.ktor.plugin.Koin

fun testHttpApplication(
    module: ApplicationModule,
    block: suspend ApplicationTestBuilder.() -> Unit,
) = testHttpApplication(listOf(module), block)

fun testHttpApplication(
    modules: List<ApplicationModule>,
    block: suspend ApplicationTestBuilder.() -> Unit,
) = testApplication {
    val applicationModules = mutableListOf(testingModule)
    applicationModules.addAll(modules)

    application {
        configureExceptionHandler()
        configureSerialization()

        install(Koin) { applicationModules.forEach { module -> modules(module.container) } }
        applicationModules.forEach { module -> module.application.invoke(this) }
    }

    block()
}
