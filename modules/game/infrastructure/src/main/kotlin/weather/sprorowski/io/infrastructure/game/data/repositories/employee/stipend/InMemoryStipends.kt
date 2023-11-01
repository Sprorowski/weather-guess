package weather.sprorowski.io.infrastructure.game.data.repositories.employee.stipend

import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.game.employee.stipend.Stipend
import weather.sprorowski.io.domain.game.employee.stipend.Stipends

class InMemoryStipends() : Stipends {
    private val items = mutableListOf(
        Stipend(Stipend.Id("HEALTH_INSURANCE"), "Car", Stipend.Type.HEALTH, Money.usd(100.0)),
        Stipend(Stipend.Id("HOME_OFFICE"), "Lamp", Stipend.Type.HOME_OFFICE, Money.usd(100.0)),
        Stipend(Stipend.Id("EMPLOYEE_SUCCESS"), "Funk album", Stipend.Type.EMPLOYEE_SUCCESS, Money.usd(100.0)),
    )

    override suspend fun load(): List<Stipend> {
        return items
    }

    override suspend fun loadById(id: Stipend.Id): Stipend {
        return items.firstOrNull { it.id == id } ?: throw Stipend.NotFound(id)
    }

    override suspend fun store(stipend: Stipend) {
        items.add(stipend)
    }
}
