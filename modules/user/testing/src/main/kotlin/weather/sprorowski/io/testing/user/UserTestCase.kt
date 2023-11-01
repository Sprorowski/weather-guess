package weather.sprorowski.io.testing.user

import weather.sprorowski.io.config.ApplicationModule
import weather.sprorowski.io.config.user.userModule
import weather.sprorowski.io.testing.HttpTestCase

interface UserTestCase : HttpTestCase {
    override fun config(): ApplicationModule = userModule(applicationConfig)
}
