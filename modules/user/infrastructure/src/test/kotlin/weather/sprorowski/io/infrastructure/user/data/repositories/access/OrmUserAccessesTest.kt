package weather.sprorowski.io.infrastructure.user.data.repositories.access

import weather.sprorowski.io.infrastructure.user.data.tables.UserAccessTable
import weather.sprorowski.io.testing.ClockSpawner
import weather.sprorowski.io.testing.matchQuery
import weather.sprorowski.io.testing.user.spawner.UserAccessSpawner
import io.kotest.common.runBlocking
import io.mockk.*
import io.tcds.orm.Param
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrmUserAccessesTest {
    private val userAccessTable: UserAccessTable = mockk()
    private val repository = OrmUserAccesses(userAccessTable)

    private val arthurDentAccess = UserAccessSpawner.arthurDentAccess()
    private val revokedAtColumn = UserAccessTable(mockk()).revokedAt

    companion object {
        private const val USER_ID = "user-xxx"
    }

    init {
        every { userAccessTable.revokedAt } returns revokedAtColumn

        UserAccessTable(mockk()).let {
            every { userAccessTable.id } returns it.id
        }
    }

    @Test
    fun `given the id then load from repository`() = runBlocking {
        coEvery { userAccessTable.loadBy(any()) } returns arthurDentAccess

        val entry = repository.loadNonRevoked(USER_ID)

        Assertions.assertEquals(arthurDentAccess, entry)
        coVerify(exactly = 1) { userAccessTable.loadBy(matchQuery { "WHERE id = `user-xxx` AND revoked_at IS NULL" }) }
    }

    @Test
    fun `given a token access then insert into the repository`() = runBlocking {
        coEvery { userAccessTable.insert(any()) } just runs

        repository.register(arthurDentAccess)

        coVerify(exactly = 1) { userAccessTable.insert(arthurDentAccess) }
    }

    @Test
    fun `given an user access then call the update`() = runBlocking {
        coEvery { userAccessTable.update(params = any(), where = any()) } just runs

        repository.revoke(UserAccessSpawner.arthurDentAccessRevoked())

        coVerify(exactly = 1) {
            userAccessTable.update(
                listOf(Param(revokedAtColumn, value = ClockSpawner.onMarch())),
                matchQuery { "WHERE id = `user-access-token-xxx`" },
            )
        }
    }
}
