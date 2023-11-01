package weather.sprorowski.io.application.game.employee.stipend.create.document

import weather.sprorowski.io.application.Command
import weather.sprorowski.io.domain.file.File
import weather.sprorowski.io.domain.game.employee.stipend.Stipend

data class AttachExpenseDocumentCommand(
    val file: File,
    val stipendId: Stipend.Id,
) : Command<Unit>
