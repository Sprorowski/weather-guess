package weather.sprorowski.io.infrastructure.table

import weather.sprorowski.io.domain.access.Scope
import io.tcds.orm.OrmResultSet
import io.tcds.orm.Table
import io.tcds.orm.connection.Connection
import io.tcds.orm.extension.*
import java.time.LocalDateTime

@Suppress("MemberVisibilityCanBePrivate")
class AccessAuditTable(connection: Connection) : Table<AccessAuditTable.Model>(
    connection = connection,
    table = "access_audit",
) {
    enum class Access { ALLOWED, DENIED }
    data class Model(
        val accessId: String?,
        val access: Access,
        val scope: Scope,
        val method: String,
        val path: String,
        val createdAt: LocalDateTime,
    )

    val accessId = varcharNullable("access_id") { it.accessId }
    val access = enum("access") { it.access }
    val scope = enum("scope") { it.scope }
    val method = varchar("method") { it.method }
    val path = varchar("path") { it.path }
    val createdAt = datetime("created_at") { it.createdAt }

    override fun entry(row: OrmResultSet): Model = Model(
        accessId = row.nullable(accessId),
        scope = row.get(scope),
        access = row.get(access),
        method = row.get(method),
        path = row.get(path),
        createdAt = row.get(createdAt),
    )
}
