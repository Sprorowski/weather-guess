package weather.sprorowski.io.application.game.employee.stipend.create

import weather.sprorowski.io.application.CommandHandler
import weather.sprorowski.io.domain.game.employee.stipend.Stipend
import weather.sprorowski.io.domain.game.employee.stipend.Stipends

class CreateStipendCommandHandler(
    private val stipends: Stipends,
) : CommandHandler<CreateStipendCommand, Unit> {
    override suspend fun handle(command: CreateStipendCommand) {
        val stipend = Stipend.create(command.description, command.type, command.amount)

        stipends.store(stipend)
    }
}
