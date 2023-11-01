package weather.sprorowski.io.application.game.employee.stipend.create

import weather.sprorowski.io.application.Command
import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.game.employee.stipend.Stipend

data class CreateStipendCommand(
    val description: String,
    val type: Stipend.Type,
    val amount: Money,
) : Command<Unit>
