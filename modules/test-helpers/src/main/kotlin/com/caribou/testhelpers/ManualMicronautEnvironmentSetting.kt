package com.caribou.testhelpers

import io.micronaut.context.env.Environment.ENVIRONMENTS_PROPERTY
import org.junit.jupiter.api.BeforeAll

open class ManualMicronautEnvironmentSetting {
    companion object {
        @BeforeAll
        @JvmStatic
        internal fun setUp() {
            // As there's a bug with the '@MicronautTest' annotation and it
            // cannot be used, this is the only way to change the environment.
            // See https://github.com/micronaut-projects/micronaut-core/issues/4556
            System.setProperty(ENVIRONMENTS_PROPERTY, TEST_ENVIRONMENT)
        }
    }
}
