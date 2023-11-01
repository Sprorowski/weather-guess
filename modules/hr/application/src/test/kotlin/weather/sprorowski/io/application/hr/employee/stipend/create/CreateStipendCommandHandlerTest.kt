package weather.sprorowski.io.application.hr.employee.stipend.create

import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.hr.employee.stipend.Stipend
import weather.sprorowski.io.domain.hr.employee.stipend.Stipends
import weather.sprorowski.io.testing.TestingUuid
import weather.sprorowski.io.testing.hr.employee.stipend.spawner.StipendSpawner
import weather.sprorowski.io.testing.matchEquals
import io.kotest.common.runBlocking
import io.mockk.*
import org.junit.jupiter.api.Test

class CreateStipendCommandHandlerTest {
    private val stipends: Stipends = mockk()
    private val handler = CreateStipendCommandHandler(stipends)
    private val command = CreateStipendCommand("New Computer", Stipend.Type.HOME_OFFICE, Money.usd(100))

    @Test
    fun `given stipend data then create stipend`() {
        TestingUuid.stack("stipend-xxx")
        coEvery { stipends.store(any()) } just runs

        runBlocking { handler.handle(command) }

        coVerify { stipends.store(matchEquals { StipendSpawner.computer() }) }
    }
}
