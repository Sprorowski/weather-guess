package weather.sprorowski.io.application.game.employee.stipend.list

import weather.sprorowski.io.application.QueryHandler
import weather.sprorowski.io.application.game.employee.stipend.StipendDto
import weather.sprorowski.io.domain.game.employee.stipend.Stipends

class ListStipendsQueryHandler(
    private val stipends: Stipends,
) : QueryHandler<ListStipendsQuery, List<StipendDto>> {
    override suspend fun handle(query: ListStipendsQuery): List<StipendDto> {
        return stipends.load().map {
            StipendDto.fromStipend(it)
        }
    }
}
