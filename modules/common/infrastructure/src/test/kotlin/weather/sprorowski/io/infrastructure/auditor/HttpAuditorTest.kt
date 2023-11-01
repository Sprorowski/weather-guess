package weather.sprorowski.io.infrastructure.auditor

import weather.sprorowski.io.domain.access.Scope
import weather.sprorowski.io.domain.access.exception.UserOperationNotAllowed
import weather.sprorowski.io.infrastructure.table.AccessAuditTable
import weather.sprorowski.io.testing.ClockSpawner
import weather.sprorowski.io.testing.createTestingToken
import weather.sprorowski.io.testing.freezeClock
import io.kotest.common.runBlocking
import io.mockk.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HttpAuditorTest {
    companion object {
        private val TOKEN_NON_AUTHORIZED = createTestingToken(emptyList())
        private val TOKEN_AUTHORIZED = createTestingToken(listOf(Scope.EMPLOYEE_EXPENSE_VIEW))
    }

    private val accessAuditTable: AccessAuditTable = mockk()
    private val auditor = HttpAuditor(accessAuditTable)

    private val accessAllowed = AccessAuditTable.Model(
        accessId = "user-access-token-xxx",
        access = AccessAuditTable.Access.ALLOWED,
        scope = Scope.EMPLOYEE_EXPENSE_VIEW,
        method = "POST",
        path = "/foo/bar",
        createdAt = ClockSpawner.now(),
    )

    private val accessDenied = AccessAuditTable.Model(
        accessId = "user-access-token-xxx",
        access = AccessAuditTable.Access.DENIED,
        scope = Scope.EMPLOYEE_EXPENSE_VIEW,
        method = "POST",
        path = "/foo/bar",
        createdAt = ClockSpawner.now(),
    )

    @Test
    fun `when user has no access to given scope then log attempt and throw a not allowed exception`() = freezeClock {
        every { accessAuditTable.insert(any()) } just runs

        assertThrows<UserOperationNotAllowed> {
            runBlocking { auditor.audit(TOKEN_NON_AUTHORIZED, Scope.EMPLOYEE_EXPENSE_VIEW, "POST", "/foo/bar") }
        }

        verify(exactly = 1) {
            accessAuditTable.insert(accessDenied)
        }
    }

    @Test
    fun `when a user has access to given scope then log access`() = freezeClock {
        every { accessAuditTable.insert(any()) } just runs

        runBlocking { auditor.audit(TOKEN_AUTHORIZED, Scope.EMPLOYEE_EXPENSE_VIEW, "POST", "/foo/bar") }

        verify(exactly = 1) {
            accessAuditTable.insert(accessAllowed)
        }
    }
}
