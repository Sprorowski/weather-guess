package weather.sprorowski.io.domain.extension

fun <T> T?.onNull(block: () -> T): T = this ?: block()
