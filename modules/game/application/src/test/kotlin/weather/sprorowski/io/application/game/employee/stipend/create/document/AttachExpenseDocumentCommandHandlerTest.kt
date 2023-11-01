package weather.sprorowski.io.application.game.employee.stipend.create.document

import weather.sprorowski.io.domain.file.File
import weather.sprorowski.io.domain.file.Storage
import weather.sprorowski.io.domain.game.employee.stipend.Stipend
import weather.sprorowski.io.domain.game.employee.stipend.Stipends
import weather.sprorowski.io.testing.game.employee.stipend.spawner.StipendSpawner
import weather.sprorowski.io.testing.matchEquals
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Test

class AttachExpenseDocumentCommandHandlerTest {
    private val storage: Storage = mockk()
    private val stipends: Stipends = mockk()
    private val stipend = StipendSpawner.computer()
    private val handler = AttachExpenseDocumentCommandHandler(storage, stipends)
    private val command = AttachExpenseDocumentCommand(File("New Stipend", "/tmp", ".png", File.Storage.LOCAL), Stipend.Id("stipend-xxx"))

    @Test
    fun `given stipend id it should call aws with file`() {
        coEvery { stipends.loadById(any()) } returns stipend
        coEvery { storage.store(any(), any(), any()) } returns
            File("New Stipend", "/tmp", ".png", File.Storage.AWS_S3)

        runBlocking { handler.handle(command) }

        coVerify {
            storage.store(
                "stipends",
                "stipend-xxx",
                matchEquals { File("New Stipend", "/tmp", ".png", File.Storage.LOCAL) },
            )
        }
    }
}
