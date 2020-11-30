package com.caribou.base.utils

import kotlin.math.roundToInt

// This function allows us to create float ranges like `0.0f..100.0f step 0.5f`
infix fun ClosedRange<Float>.step(step: Float): Iterable<Float> {
    require(start.isFinite())
    require(endInclusive.isFinite())
    require(step > 0.0) { "Step must be positive, was: $step." }
    val sequence = generateSequence(start) { previous ->
        if (previous == Float.POSITIVE_INFINITY) return@generateSequence null
        val next = ((previous + step) * 100).roundToInt().toFloat() / 100f // Only keep 1 fraction digit.
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}
