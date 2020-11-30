package com.caribou.testhelpers.db

import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeEach
import javax.inject.Inject

open class FlywayResetDbTest {

    @Inject
    lateinit var flyway: Flyway

    @BeforeEach
    fun beforeEach() {
        flyway.clean()
        flyway.migrate()
    }
}
