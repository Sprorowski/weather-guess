package weather.sprorowski.io.domain

import java.math.BigDecimal
import kotlin.math.absoluteValue

data class Money(val value: String, val currency: Currency) {
    enum class Currency {
        USD,
    }

    constructor(value: BigDecimal, currency: Currency) : this(
        value = value.toString(),
        currency = currency,
    )

    constructor(value: Float, currency: Currency) : this(
        value = value.toString(),
        currency = currency,
    )

    constructor(value: Double, currency: Currency) : this(
        value = value.toString(),
        currency = currency,
    )

    companion object {
        fun usd(value: Float): Money = Money(value = value, currency = Currency.USD)
        fun usd(value: Double): Money = Money(value = value, currency = Currency.USD)
        fun usd(value: Int): Money = Money(value = value.toDouble(), currency = Currency.USD)
    }

    fun discounting(): Money {
        return Money(value.toDouble().absoluteValue.unaryMinus(), currency)
    }
}
