package weather.sprorowski.io.testing

import weather.sprorowski.io.domain.file.File

class FileSpawner {
    companion object {
        fun notaFiscalFile(): File = File(
            name = "nota-fiscal.pdf",
            path = "/tmp/nota-fiscal.pdf",
            type = "application/pdf",
            storage = File.Storage.AWS_S3,
        )

        fun driverLicenseFile(): File = File(
            name = "driver-license.pdf",
            path = "/tmp/driver-license.pdf",
            type = "application/pdf",
            storage = File.Storage.AWS_S3,
        )
    }
}
