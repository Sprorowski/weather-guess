package weather.sprorowski.io.testing

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic

class TestingUuid {
    companion object {
        fun stack(vararg id: String) {
            val stack = id.toMutableList()

            mockkStatic(weather.sprorowski.io.domain.Uuid::class)
            every { weather.sprorowski.io.domain.Uuid.create() } returnsMany stack
        }

        fun increment(prefix: String, increment: Int) {
            var incrementing = 0
            mockkStatic(weather.sprorowski.io.domain.Uuid::class)

            every { weather.sprorowski.io.domain.Uuid.create() } answers { incrementing += increment; "$prefix-$incrementing" }
        }
    }
}

fun freezeUuid(uuid: String) =
    mockkStatic(weather.sprorowski.io.domain.Uuid::class).let { every { weather.sprorowski.io.domain.Uuid.create() } returns uuid }

fun unfreezeUuid() = unmockkStatic(weather.sprorowski.io.domain.Uuid::class)
fun <T> freezeUuid(uuid: String, block: () -> T): T = freezeUuid(uuid).let { block().apply { unfreezeClock() } }

suspend fun <T> coFreezeUuid(uuid: String, block: suspend () -> T): T {
    return freezeUuid(uuid).let { block().apply { unfreezeClock() } }
}
