package weather.sprorowski.io.domain.hr.employee.stipend

import weather.sprorowski.io.domain.hr.ErrorCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class StipendNotFoundTest {

    @Test
    fun `create not found exception`() {
        val exception = Stipend.NotFound(Stipend.Id("STIPEND_ID"))

        Assertions.assertEquals("Stipend Not Found", exception.message)
        Assertions.assertEquals(ErrorCode.STIPEND_NOT_FOUND, exception.code)
        Assertions.assertEquals(mapOf("id" to "STIPEND_ID"), exception.details)
    }
}
