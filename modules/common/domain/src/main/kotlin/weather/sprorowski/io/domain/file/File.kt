package weather.sprorowski.io.domain.file

data class File(
    val name: String,
    val path: String,
    val type: String,
    val storage: Storage,
) {
    enum class Storage { LOCAL, AWS_S3 }

    fun extension() = name.split(".").last()
}
