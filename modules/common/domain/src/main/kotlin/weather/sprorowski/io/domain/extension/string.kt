package weather.sprorowski.io.domain.extension

fun String.trimLines() = this.trimIndent().replace("\n", "")
