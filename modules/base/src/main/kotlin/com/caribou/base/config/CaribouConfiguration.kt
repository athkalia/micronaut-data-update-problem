package com.caribou.base.config

import io.micronaut.context.annotation.ConfigurationProperties
import javax.validation.constraints.NotBlank

// See https://docs.micronaut.io/latest/guide/index.html#immutableConfig this way we can validate our config to make
// sure it's loaded properly (validated at runtime, at application start)
@ConfigurationProperties("caribou")
interface CaribouConfiguration {

    @get:NotBlank
    val debug: Boolean

}
