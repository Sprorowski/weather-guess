package weather.sprorowski.io.application.hr.employee.stipend.get

import weather.sprorowski.io.application.Query
import weather.sprorowski.io.application.hr.employee.stipend.StipendDto
import weather.sprorowski.io.domain.hr.employee.stipend.Stipend

data class GetStipendQuery(val id: Stipend.Id) : Query<StipendDto>
