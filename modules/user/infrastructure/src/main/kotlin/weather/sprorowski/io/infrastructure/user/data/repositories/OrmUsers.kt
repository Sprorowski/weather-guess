package weather.sprorowski.io.infrastructure.user.data.repositories

import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.Users
import weather.sprorowski.io.infrastructure.user.data.tables.UserEmailTable
import weather.sprorowski.io.infrastructure.user.data.tables.UserTable
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.where

class OrmUsers(
    private val userTable: UserTable,
    private val userEmail: UserEmailTable,
    private val connection: Connection,
) : Users {
    override suspend fun loadById(id: String): User {
        return userTable.loadById(id) ?: throw User.NotFound()
    }

    override suspend fun findByEmail(email: String): User? {
        return userEmail.loadBy(
            where(userEmail.email equalsTo email),
            listOf(userEmail.createdAt.desc()),
        )?.let {
            userTable.loadById(it.userId)!!
        }
    }

    override suspend fun exists(email: String): Boolean {
        return userEmail.exists(
            where(userEmail.email equalsTo email),
        )
    }

    override suspend fun register(user: User) {
        val email = UserEmailTable.Model(
            userId = user.id,
            email = user.email.address,
            createdAt = user.createdAt,
            deletedAt = null,
        )

        connection.transaction {
            userTable.insert(user)
            userEmail.insert(email)
        }
    }
}
