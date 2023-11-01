package weather.sprorowski.io.infrastructure.table

import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.testing.ClockSpawner
import io.mockk.mockk
import io.tcds.orm.MapOrmResultSet
import io.tcds.orm.connection.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AccessAuditTableTest {
    private val connection: Connection = mockk()
    private val table = AccessAuditTable(connection)

    private val entry = AccessAuditTable.Model(
        accessId = "user-access-token-xxx",
        access = AccessAuditTable.Access.ALLOWED,
        scope = Scope.EMPLOYEE_EXPENSE_VIEW,
        method = "POST",
        path = "/foo/bar",
        createdAt = ClockSpawner.now(),
    )

    private val values = mapOf(
        "access_id" to "user-access-token-xxx",
        "access" to "ALLOWED",
        "scope" to "EMPLOYEE_EXPENSE_VIEW",
        "method" to "POST",
        "path" to "/foo/bar",
        "created_at" to ClockSpawner.now(),
    )

    @Test
    fun `given an entry then return its values`() = Assertions.assertEquals(values, table.values(entry))

    @Test
    fun `given the values then return its entry`() = Assertions.assertEquals(entry, table.entry(MapOrmResultSet(values)))
}
