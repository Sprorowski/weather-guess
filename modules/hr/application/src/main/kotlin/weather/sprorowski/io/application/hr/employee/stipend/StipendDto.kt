package weather.sprorowski.io.application.hr.employee.stipend

import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.hr.employee.stipend.Stipend

data class StipendDto(val id: String, val description: String, val type: Stipend.Type, val amount: Money) {
    companion object {
        fun fromStipend(stipend: Stipend): StipendDto {
            return StipendDto(stipend.id.value, stipend.description, stipend.type, stipend.amount)
        }
    }
}


