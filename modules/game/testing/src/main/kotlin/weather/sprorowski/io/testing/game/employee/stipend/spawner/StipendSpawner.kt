package weather.sprorowski.io.testing.game.employee.stipend.spawner

import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.game.employee.stipend.Stipend

class StipendSpawner {
    companion object {
        fun computer(): Stipend {
            return Stipend(Stipend.Id("stipend-xxx"), "New Computer", Stipend.Type.HOME_OFFICE, Money.usd(100))
        }

        fun healthInsurance(): Stipend {
            return Stipend(Stipend.Id("stipend-xxx"), "Health Insurance", Stipend.Type.HEALTH, Money.usd(100))
        }

        fun employeeSuccess(): Stipend {
            return Stipend(Stipend.Id("stipend-xxx"), "Employee Success", Stipend.Type.EMPLOYEE_SUCCESS, Money.usd(100))
        }
    }
}
