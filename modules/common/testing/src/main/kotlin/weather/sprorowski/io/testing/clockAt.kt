package weather.sprorowski.io.testing

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import java.time.*

fun freezeClock(at: String = "2022-12-18T05:48:52Z") {
    mockkStatic(Clock::class)
    every { Clock.systemDefaultZone() } returns Clock.fixed(Instant.parse(at), ZoneOffset.UTC)
}

fun freezeClock(at: LocalDateTime) {
    mockkStatic(Clock::class)
    every { Clock.systemDefaultZone() } returns Clock.fixed(at.toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
}

fun unfreezeClock() = unmockkStatic(Clock::class)

suspend fun <T> coFreezeClock(at: String = "2022-12-18T05:48:52Z", block: suspend () -> T): T {
    return freezeClock(at).let { block().apply { unfreezeClock() } }
}

suspend fun <T> coFreezeClock(at: LocalDateTime, block: suspend () -> T): T {
    return freezeClock(at).let { block().apply { unfreezeClock() } }
}

fun <T> freezeClock(at: String = "2022-12-18T05:48:52Z", block: () -> T): T {
    return freezeClock(at).let { block().apply { unfreezeClock() } }
}

class ClockSpawner {
    companion object {
        fun now(): LocalDateTime = LocalDateTime.of(2022, Month.DECEMBER, 18, 5, 48, 52)
        fun createdAt(): LocalDateTime = LocalDateTime.of(2022, Month.DECEMBER, 18, 5, 48, 52)
        fun scheduledAt(): LocalDateTime = LocalDateTime.of(2023, Month.FEBRUARY, 21, 8, 30, 33)
        fun pixCreationDate(): LocalDateTime = LocalDateTime.of(2022, Month.DECEMBER, 18, 5, 48, 52)
        fun birthDate(): LocalDate = LocalDate.of(1960, Month.JANUARY, 16)

        fun onFebruary(): LocalDateTime = LocalDateTime.of(2023, Month.FEBRUARY, 22, 10, 15, 20)
        fun onMarch(): LocalDateTime = LocalDateTime.of(2023, Month.MARCH, 22, 10, 15, 30)
    }
}
