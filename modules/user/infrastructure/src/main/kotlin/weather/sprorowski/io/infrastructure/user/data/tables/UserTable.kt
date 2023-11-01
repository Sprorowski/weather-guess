package weather.sprorowski.io.infrastructure.user.data.tables

import weather.sprorowski.io.domain.user.User
import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.datetime
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.varchar
import io.tcds.orm.extension.where

@Suppress("MemberVisibilityCanBePrivate")
class UserTable(
    connection: Connection,
    val userEmail: UserEmailTable,
) : EntityTable<User, String>(
    table = "users",
    connection = connection,
) {
    override val id = varchar("id") { it.id }
    val name = varchar("name") { it.name }
    val createdAt = datetime("created_at") { it.createdAt }

    override fun entry(row: OrmResultSet): User {
        val id = row.get(id)

        return User(
            id = id,
            name = row.get(name),
            email = User.Email(
                userEmail.loadBy(
                    where(userEmail.userId equalsTo id),
                    listOf(userEmail.createdAt.desc()),
                )!!.email,
            ),
            createdAt = row.get(createdAt),
        )
    }
}
