package weather.sprorowski.io.infrastructure.user.data.tables

import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.datetime
import io.tcds.orm.extension.datetimeNullable
import io.tcds.orm.extension.varchar
import java.time.LocalDateTime

@Suppress("MemberVisibilityCanBePrivate")
class UserEmailTable(connection: Connection) : Table<UserEmailTable.Model>(
    connection = connection,
    table = "user_emails",
) {
    data class Model(
        val userId: String,
        val email: String,
        val createdAt: LocalDateTime,
        val deletedAt: LocalDateTime?,
    )

    val userId = varchar("user_id") { it.userId }
    val email = varchar("email") { it.email }
    val createdAt = datetime("created_at") { it.createdAt }
    val deletedAt = datetimeNullable("deleted_at") { it.deletedAt }

    override fun entry(row: OrmResultSet): Model = Model(
        userId = row.get(userId),
        email = row.get(email),
        createdAt = row.get(createdAt),
        deletedAt = row.nullable(deletedAt),
    )
}
