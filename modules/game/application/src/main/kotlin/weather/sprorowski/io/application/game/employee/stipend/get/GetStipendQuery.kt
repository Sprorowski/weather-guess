package weather.sprorowski.io.application.game.employee.stipend.get

import weather.sprorowski.io.application.Query
import weather.sprorowski.io.application.game.employee.stipend.StipendDto
import weather.sprorowski.io.domain.game.employee.stipend.Stipend

data class GetStipendQuery(val id: Stipend.Id) : Query<StipendDto>
