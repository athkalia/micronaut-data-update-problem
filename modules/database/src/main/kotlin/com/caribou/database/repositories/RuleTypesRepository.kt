package com.caribou.database.repositories

import com.caribou.database.domain.RuleType
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.MYSQL)
@Repository
interface RuleTypesRepository : CrudRepository<RuleType, String> {

    override fun findAll(): List<RuleType>

}
