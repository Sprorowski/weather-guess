package weather.sprorowski.io.application.hr.employee.stipend.list

import weather.sprorowski.io.application.Query
import weather.sprorowski.io.application.hr.employee.stipend.StipendDto

data class ListStipendsQuery(val page: Int) : Query<List<StipendDto>>
