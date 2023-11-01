package weather.sprorowski.io.domain.file

interface Storage {
    suspend fun store(path: String, name: String, file: File): File
}
