package weather.sprorowski.io.domain.extension.suspend

suspend fun <T> T?.onNull(block: suspend () -> T): T = this ?: block()
