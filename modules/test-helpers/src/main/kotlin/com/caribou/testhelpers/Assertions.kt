package com.caribou.testhelpers

import org.assertj.core.api.Assertions
import org.assertj.core.data.TemporalUnitWithinOffset
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

fun assertDatesWereJustCreated(vararg dates: OffsetDateTime?) {
    dates.forEach {
        Assertions.assertThat(it).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }
}
