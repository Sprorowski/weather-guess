package weather.sprorowski.io.application.game.employee.stipend.get

import weather.sprorowski.io.application.QueryHandler
import weather.sprorowski.io.application.game.employee.stipend.StipendDto
import weather.sprorowski.io.domain.game.employee.stipend.Stipends

class GetStipendQueryHandler(
    private val stipends: Stipends,
) : QueryHandler<GetStipendQuery, StipendDto> {
    override suspend fun handle(query: GetStipendQuery): StipendDto {
        return stipends.loadById(query.id).let {
            StipendDto.fromStipend(it)
        };
    }
}
