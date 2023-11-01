package weather.sprorowski.io.infrastructure.user.data.tables

import weather.sprorowski.io.domain.user.access.UserAccess
import io.tcds.orm.EntityTable
import io.tcds.orm.OrmResultSet
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.datetime
import io.tcds.orm.extension.datetimeNullable
import io.tcds.orm.extension.varchar

@Suppress("MemberVisibilityCanBePrivate")
class UserAccessTable(
    connection: Connection,
) : EntityTable<UserAccess, String>(
    connection = connection,
    table = "user_accesses",
) {
    override val id = varchar("id") { it.id }
    val userId = varchar("user_id") { it.userId }
    val createdAt = datetime("created_at") { it.createdAt }
    val revokedAt = datetimeNullable("revoked_at") { it.revokedAt }

    override fun entry(row: OrmResultSet): UserAccess = UserAccess(
        id = row.get(id),
        userId = row.get(userId),
        createdAt = row.get(createdAt),
        revokedAt = row.nullable(revokedAt),
    )
}
