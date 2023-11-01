package weather.sprorowski.io.config.user

import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.Test
import org.koin.test.check.checkModules
import org.koin.test.junit5.ClosingKoinTest
import weather.sprorowski.io.config.container as coreContainer
import weather.sprorowski.io.config.user.container as userContainer
import weather.sprorowski.io.testing.applicationConfig as coreApplicationConfig
import weather.sprorowski.io.testing.user.applicationConfig as userApplicationConfig

internal class UserContainerTest : ClosingKoinTest {
    @Test
    fun `test container setup`() = testApplication {
        checkModules { coreContainer(coreApplicationConfig); userContainer(userApplicationConfig) }
    }
}
