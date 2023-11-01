package weather.sprorowski.io.presenter.plugins

import io.ktor.server.application.Application
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication

fun testCoreApplication(
    application: Application.() -> Unit,
    block: suspend ApplicationTestBuilder.() -> Unit,
) = testApplication {
    application {
        configureExceptionHandler()
        configureSerialization()
        application()
    }

    block()
}
