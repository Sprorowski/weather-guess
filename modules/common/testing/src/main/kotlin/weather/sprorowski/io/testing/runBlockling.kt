package weather.sprorowski.io.testing

import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

fun <T> runBlocking(clock: LocalDateTime, f: suspend () -> T): T = runBlocking { coFreezeClock(clock) { f() } }
fun <T> runBlocking(uuid: String, f: suspend () -> T): T = runBlocking { coFreezeUuid(uuid) { f() } }

fun <T> runBlocking(clock: LocalDateTime, uuid: String, f: suspend () -> T): T = runBlocking {
    coFreezeClock(clock) { coFreezeUuid(uuid) { f() } }
}
