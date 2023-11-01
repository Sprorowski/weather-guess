package weather.sprorowski.io.infrastructure.user.data.repositories.access

import weather.sprorowski.io.domain.user.User
import weather.sprorowski.io.domain.user.access.UserAccess
import weather.sprorowski.io.domain.user.access.UserAccesses
import weather.sprorowski.io.infrastructure.user.data.tables.UserAccessTable
import io.tcds.orm.Param
import io.tcds.orm.extension.and
import io.tcds.orm.extension.equalsTo
import io.tcds.orm.extension.isNull
import io.tcds.orm.extension.where

class OrmUserAccesses(
    private val userAccessTable: UserAccessTable,
) : UserAccesses {
    override suspend fun loadNonRevoked(id: String): UserAccess {
        return userAccessTable.loadBy(
            where(userAccessTable.id equalsTo id) and userAccessTable.revokedAt.isNull(),
        ) ?: throw User.NotFound()
    }

    override suspend fun register(access: UserAccess) = userAccessTable.insert(access)

    override suspend fun revoke(access: UserAccess) = userAccessTable.update(
        listOf(Param(userAccessTable.revokedAt, access.revokedAt)),
        where(userAccessTable.id equalsTo access.id),
    )
}
