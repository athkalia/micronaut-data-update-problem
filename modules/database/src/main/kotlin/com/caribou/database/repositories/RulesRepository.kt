package com.caribou.database.repositories

import com.caribou.database.domain.Rule
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.MYSQL)
interface RulesRepository : CrudRepository<Rule, String> {

    override fun findAll(): List<Rule>

}
