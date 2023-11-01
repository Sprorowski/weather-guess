package weather.sprorowski.io.application.hr.employee.stipend.get

import weather.sprorowski.io.application.hr.employee.stipend.StipendDto
import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.hr.employee.stipend.Stipend
import weather.sprorowski.io.domain.hr.employee.stipend.Stipends
import weather.sprorowski.io.testing.hr.employee.stipend.spawner.StipendSpawner
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GetStipendQueryHandlerTest {
    private val stipends: Stipends = mockk()
    private val handler = GetStipendQueryHandler(stipends)
    private val query = GetStipendQuery(Stipend.Id("stipend-xxx"))
    private val stipend = StipendSpawner.computer()

    @Test
    fun `given stipend data then create stipend`() {
        coEvery { stipends.loadById(any()) } returns stipend

        val response = runBlocking { handler.handle(query) }

        coVerify(exactly = 1) { stipends.loadById(Stipend.Id("stipend-xxx")) }
        Assertions.assertEquals(
            StipendDto("stipend-xxx", "New Computer", Stipend.Type.HOME_OFFICE, Money.usd(100)),
            response,
        )
    }
}
