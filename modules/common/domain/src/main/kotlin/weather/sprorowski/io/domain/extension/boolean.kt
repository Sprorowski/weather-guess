package weather.sprorowski.io.domain.extension

fun Boolean.onFalse(block: () -> Unit) {
    when (this) {
        false -> block()
        true -> return
    }
}

fun Boolean.onTrue(block: () -> Unit) {
    when (this) {
        false -> return
        true -> block()
    }
}
