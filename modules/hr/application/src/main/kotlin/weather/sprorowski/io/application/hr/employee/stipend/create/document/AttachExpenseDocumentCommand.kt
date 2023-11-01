package weather.sprorowski.io.application.hr.employee.stipend.create.document

import weather.sprorowski.io.application.Command
import weather.sprorowski.io.domain.file.File
import weather.sprorowski.io.domain.hr.employee.stipend.Stipend

data class AttachExpenseDocumentCommand(
    val file: File,
    val stipendId: Stipend.Id,
) : Command<Unit>
