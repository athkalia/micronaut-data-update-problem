package com.caribou.database.domain

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import java.time.OffsetDateTime
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "RULE_TYPES")
data class RuleType(
    @field:Id @field:GeneratedValue var id: String? = null,
    val name: String,
    val paramsCount: Int,
    @DateCreated var createdAt: OffsetDateTime? = null,
    @DateUpdated var updatedAt: OffsetDateTime? = null
)
