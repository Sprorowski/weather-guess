package weather.sprorowski.io.config.hr

import weather.sprorowski.io.testing.applicationConfig
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import org.koin.test.check.checkModules
import org.koin.test.junit5.ClosingKoinTest

internal class HrContainerTest : ClosingKoinTest {
    @Test
    fun `test container setup`() = testApplication {
        checkModules {
            weather.sprorowski.io.config.container(applicationConfig); container(hrApplicationConfig)
        }
    }
}
