package weather.sprorowski.io.infrastructure.user.data.repositories

import weather.sprorowski.io.infrastructure.user.data.tables.UserEmailTable
import weather.sprorowski.io.infrastructure.user.data.tables.UserTable
import weather.sprorowski.io.testing.matchQuery
import weather.sprorowski.io.testing.user.spawner.UserSpawner
import io.kotest.common.runBlocking
import io.mockk.*
import io.tcds.orm.connection.Connection
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class OrmUsersTest {
    companion object {
        private const val USER_ID = "user-xxx"
    }

    private val userTable: UserTable = mockk()
    private val userEmail: UserEmailTable = mockk()
    private val connection: Connection = mockk()
    private val repository = OrmUsers(userTable, userEmail, connection)

    private val arthurDent = UserSpawner.arthurDent()
    private val email = UserEmailTable.Model(
        userId = arthurDent.id,
        email = arthurDent.email.address,
        createdAt = arthurDent.createdAt,
        deletedAt = null,
    )

    init {
        every { connection.transaction(invoke()) } just runs

        UserTable(mockk(), mockk()).let {
            every { userTable.name } returns it.name
        }

        UserEmailTable(mockk()).let {
            every { userEmail.userId } returns it.userId
            every { userEmail.email } returns it.email
            every { userEmail.createdAt } returns it.createdAt
        }
    }

    @Test
    fun `given the id then load from repository`() = runBlocking {
        coEvery { userTable.loadById(any()) } returns arthurDent

        val entry = repository.loadById(USER_ID)

        Assertions.assertEquals(arthurDent, entry)
        coVerify(exactly = 1) { userTable.loadById(USER_ID) }
    }

    @Test
    fun `given an email when loadBy is called then return a id`() = runBlocking {
        coEvery { userEmail.loadBy(any(), any()) } returns email
        coEvery { userTable.loadById(any()) } returns arthurDent

        val entry = repository.findByEmail(arthurDent.email.address)

        Assertions.assertSame(arthurDent, entry)
        coVerify(exactly = 1) {
            userEmail.loadBy(
                matchQuery { "WHERE email = `arthur.dent@galaxy.org`" },
                any(),
            )
        }
        coVerify(exactly = 1) { userTable.loadById("user-xxx") }
    }

    @Test
    fun `given an email when exists is called then verify if this email already exist`() = runBlocking {
        coEvery { userEmail.exists(any()) } returns true

        val entry = repository.exists("arthur.dent@galaxy.org")

        Assertions.assertEquals(true, entry)
        coVerify { userEmail.exists(matchQuery { "WHERE email = `arthur.dent@galaxy.org`" }) }
    }

    @Test
    fun `given a user then insert into the repository`() = runBlocking {
        coEvery { userTable.insert(any()) } just runs
        coEvery { userEmail.insert(any()) } just runs

        repository.register(arthurDent)

        coVerify(exactly = 1) { userTable.insert(arthurDent) }
        coVerify(exactly = 1) { userEmail.insert(email) }
    }
}
