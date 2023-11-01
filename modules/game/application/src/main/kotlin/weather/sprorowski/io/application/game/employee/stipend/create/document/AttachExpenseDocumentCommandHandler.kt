package weather.sprorowski.io.application.game.employee.stipend.create.document

import weather.sprorowski.io.application.CommandHandler
import weather.sprorowski.io.domain.file.Storage
import weather.sprorowski.io.domain.game.employee.stipend.Stipends

class AttachExpenseDocumentCommandHandler(
    private val storage: Storage,
    private val stipends: Stipends,
) : CommandHandler<AttachExpenseDocumentCommand, Unit> {
    override suspend fun handle(command: AttachExpenseDocumentCommand) {
        stipends.loadById( command.stipendId)
        storage.store("stipends", command.stipendId.value, command.file)
    }
}
