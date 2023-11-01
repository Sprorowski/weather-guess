package weather.sprorowski.io.application.game.employee.stipend.list

import weather.sprorowski.io.application.game.employee.stipend.StipendDto
import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.game.employee.stipend.Stipend
import weather.sprorowski.io.domain.game.employee.stipend.Stipends
import weather.sprorowski.io.testing.game.employee.stipend.spawner.StipendSpawner
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ListStipendsQueryHandlerTest {
    private val stipends: Stipends = mockk()
    private val handler = ListStipendsQueryHandler(stipends)
    private val command = ListStipendsQuery(1)

    @Test
    fun `given stipend data then create stipend`() {

        val stipendList = mutableListOf(
            StipendSpawner.computer(),
            StipendSpawner.employeeSuccess(),
            StipendSpawner.healthInsurance(),
        )
        coEvery { stipends.load() } returns stipendList

        val responseStipendList = runBlocking { handler.handle(command) }

        coVerify(exactly = 1) { stipends.load() }

        val stipendExpectedList: List<StipendDto> = mutableListOf(
            StipendDto("stipend-xxx", "New Computer", Stipend.Type.HOME_OFFICE, Money.usd(100)),
            StipendDto("stipend-xxx", "Health Insurance", Stipend.Type.HEALTH, Money.usd(100)),
            StipendDto("stipend-xxx", "Employee Success", Stipend.Type.EMPLOYEE_SUCCESS, Money.usd(100)),
        )

        Assertions.assertEquals(responseStipendList.size, 3)
        Assertions.assertTrue(stipendExpectedList.containsAll(responseStipendList))
    }
}
