package weather.sprorowski.io.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MoneyTest {
    @Test
    fun `given an amount when then get discounting amount`() {
        val amount = Money.usd(100.0)

        val discounting = amount.discounting()

        Assertions.assertEquals(Money.usd(-100.0), discounting)
    }

    @Test
    fun `given a negative amount when then get discounting amount is negative`() {
        val amount = Money.usd(-100.0)

        val discounting = amount.discounting()

        Assertions.assertEquals(Money.usd(-100.0), discounting)
    }
}
