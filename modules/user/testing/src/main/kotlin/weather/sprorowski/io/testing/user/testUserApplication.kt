package weather.sprorowski.io.testing.user

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.config.user.userModule
import weather.sprorowski.io.testing.testHttpApplication
import io.ktor.server.testing.*

fun testUserApplication(
    module: ApplicationModule,
    block: suspend ApplicationTestBuilder.() -> Unit,
) = testHttpApplication(listOf(userModule(applicationConfig), module), block)
