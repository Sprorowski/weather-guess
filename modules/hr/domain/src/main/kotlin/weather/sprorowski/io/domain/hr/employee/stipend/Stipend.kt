package weather.sprorowski.io.domain.hr.employee.stipend

import weather.sprorowski.io.domain.Money
import weather.sprorowski.io.domain.Uuid
import weather.sprorowski.io.domain.exception.EntityNotFoundException
import weather.sprorowski.io.domain.hr.ErrorCode

class Stipend(
    val id: Id,
    val description: String,
    val type: Type,
    val amount: Money,
) {
    class NotFound(id: Id) : EntityNotFoundException("Stipend", mapOf("id" to id.value), ErrorCode.STIPEND_NOT_FOUND)
    data class Id(val value: String)
    enum class Type { HEALTH, HOME_OFFICE, EMPLOYEE_SUCCESS }

    companion object {
        fun create(description: String, type: Type, amount: Money) = Stipend(
            id = Id(Uuid.create()),
            description = description,
            type = type,
            amount = amount,
        )
    }
}
