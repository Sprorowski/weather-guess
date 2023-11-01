package weather.sprorowski.io.domain.hr.employee.stipend

interface Stipends {
    suspend fun load(): List<Stipend>
    suspend fun loadById(id: Stipend.Id): Stipend
    suspend fun store(stipend: Stipend)
}
