package weather.sprorowski.io.infrastructure.user.data.tables

import weather.sprorowski.io.testing.ClockSpawner
import weather.sprorowski.io.testing.assertObjects
import weather.sprorowski.io.testing.user.spawner.UserSpawner
import io.mockk.every
import io.mockk.mockk
import io.tcds.orm.MapOrmResultSet
import io.tcds.orm.connection.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserTableTest {
    companion object {
        private const val USER_EMAIL_ADDRESS = "arthur.dent@galaxy.org"
    }

    private val connection: Connection = mockk()
    private val userEmail: UserEmailTable = mockk()

    private val table = UserTable(connection, userEmail)
    private val user = UserSpawner.arthurDent()
    private val email = UserEmailTable.Model("", USER_EMAIL_ADDRESS, ClockSpawner.now(), null)

    private val values = mapOf(
        "id" to "user-xxx",
        "name" to "Arthur Dent",
        "created_at" to ClockSpawner.now(),
    )

    init {
        UserEmailTable(mockk()).let {
            every { userEmail.userId } returns it.userId
            every { userEmail.createdAt } returns it.createdAt
        }

        every { userEmail.loadBy(any(), any()) } returns email
    }

    @Test
    fun `given an entry then return its values`() = Assertions.assertEquals(values, table.values(user))

    @Test
    fun `given the values then return its entry`() = assertObjects(user, table.entry(MapOrmResultSet(values)))
}
