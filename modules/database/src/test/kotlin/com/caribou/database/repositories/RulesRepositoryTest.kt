package com.caribou.database.repositories

import com.caribou.database.domain.Rule
import com.caribou.database.domain.RuleType
import com.caribou.database.util.FlywayResetDbTest
import com.caribou.testhelpers.TEST_ENVIRONMENT
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.TemporalUnitWithinOffset
import org.junit.jupiter.api.Test
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@MicronautTest(environments = [TEST_ENVIRONMENT])
class RulesRepositoryTest : FlywayResetDbTest() {

    @Inject
    lateinit var rulesRepository: RulesRepository

    @Inject
    lateinit var ruleTypesRepository: RuleTypesRepository

    @Test
    fun `add a rule`() {
        // given
        val ruleType = RuleType(name = "name", paramsCount = 3)
        val rule = Rule(ruleType = ruleType, parameters = "some parameters")

        // when
        ruleTypesRepository.save(ruleType)
        rulesRepository.save(rule)

        val savedRules = rulesRepository.findAll()

        // then
        assertThat(savedRules.count()).isEqualTo(1)
        val savedRule = savedRules[0]
        assertThat(savedRule.id).isEqualTo("1")
        assertThat(savedRule.parameters).isEqualTo("some parameters")
        assertThat(savedRule.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(savedRule.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `find rule by id`() {
        // given
        val ruleType = RuleType(name = "name", paramsCount = 3)
        val rule = Rule(ruleType = ruleType, parameters = "some parameters")
        ruleTypesRepository.save(ruleType)
        rulesRepository.save(rule)

        // when
        val savedRule = rulesRepository.findById("1").get()

        // then
        assertThat(savedRule.id).isEqualTo("1")
        assertThat(savedRule.parameters).isEqualTo("some parameters")
        assertThat(savedRule.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(savedRule.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `check if a rule exists by id`() {
        // given
        val ruleType = RuleType(name = "name", paramsCount = 3)
        val rule = Rule(ruleType = ruleType, parameters = "some parameters")
        ruleTypesRepository.save(ruleType)
        rulesRepository.save(rule)

        // when
        val ruleExists = rulesRepository.existsById("1")

        // then
        assertThat(ruleExists).isTrue
    }

    @Test
    fun `count rule entries`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 4)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        val rule2 = Rule(ruleType = ruleType2, parameters = "some parameters2")
        ruleTypesRepository.save(ruleType1)
        rulesRepository.save(rule1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.save(rule2)

        // when
        val rulesCount = rulesRepository.count()

        // then
        assertThat(rulesCount).isEqualTo(2)
    }

    @Test
    fun `delete rule by id`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 34)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        val rule2 = Rule(ruleType = ruleType2, parameters = "some parameters2")
        ruleTypesRepository.save(ruleType1)
        rulesRepository.save(rule1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.save(rule2)

        // when
        rulesRepository.deleteById("1")

        // then
        assertThat(rulesRepository.count()).isEqualTo(1)
        val remainingRule = rulesRepository.findAll()[0]
        assertThat(remainingRule.id).isEqualTo("2")
        assertThat(remainingRule.parameters).isEqualTo("some parameters2")
        assertThat(remainingRule.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(remainingRule.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `delete rule`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 34)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        val rule2 = Rule(ruleType = ruleType2, parameters = "some parameters2")
        ruleTypesRepository.save(ruleType1)
        rulesRepository.save(rule1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.save(rule2)

        // when
        rulesRepository.delete(rule1)

        // then
        assertThat(rulesRepository.count()).isEqualTo(1)
        assertThat(ruleTypesRepository.count()).isEqualTo(2) // Deletes don't propagate.
        val remainingRule = rulesRepository.findAll()[0]
        assertThat(remainingRule.id).isEqualTo("2")
        assertThat(remainingRule.parameters).isEqualTo("some parameters2")
        assertThat(remainingRule.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(remainingRule.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `delete all rules`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 34)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        val rule2 = Rule(ruleType = ruleType2, parameters = "some parameters2")
        ruleTypesRepository.save(ruleType1)
        rulesRepository.save(rule1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.save(rule2)

        // when
        rulesRepository.deleteAll()

        // then
        assertThat(rulesRepository.count()).isEqualTo(0)
        assertThat(ruleTypesRepository.count()).isEqualTo(2) // Deletes don't propagate.
    }


    @Test
    fun `update rule`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        ruleTypesRepository.save(ruleType1)
        rulesRepository.save(rule1)
        val savedRule = rulesRepository.findById("1").get()
        val savedRuleType = ruleTypesRepository.findById("1").get()

        // when
        rulesRepository.update(savedRule.copy(parameters = "new_params", ruleType = savedRuleType))

        // then
        assertThat(rulesRepository.count()).isEqualTo(1)
        assertThat(ruleTypesRepository.count()).isEqualTo(1)
        val updatedRule = rulesRepository.findAll()[0]
        assertThat(updatedRule.id).isEqualTo("1")
        assertThat(updatedRule.parameters).isEqualTo("new_params")
        assertThat(updatedRule.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(updatedRule.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `save multiple rules`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 34)
        val rules = listOf(
            Rule(ruleType = ruleType1, parameters = "some parameters"),
            Rule(ruleType = ruleType2, parameters = "some parameters2")
        )

        // when
        ruleTypesRepository.save(ruleType1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.saveAll(rules)

        // then
        val allSavedRules = rulesRepository.findAll()
        assertThat(allSavedRules.count()).isEqualTo(2)
        val savedRule1 = allSavedRules[0]
        assertThat(savedRule1.id).isEqualTo("1")
        assertThat(savedRule1.parameters).isEqualTo("some parameters")
        assertThat(savedRule1.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(savedRule1.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        val savedRule2 = allSavedRules[1]
        assertThat(savedRule2.id).isEqualTo("2")
        assertThat(savedRule2.parameters).isEqualTo("some parameters2")
        assertThat(savedRule2.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(savedRule2.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `delete all rule types by object`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 34)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        val rule2 = Rule(ruleType = ruleType2, parameters = "some parameters2")
        ruleTypesRepository.save(ruleType1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.save(rule1)
        rulesRepository.save(rule2)

        // when
        rulesRepository.deleteAll(listOf(rule1, rule2))

        // then
        assertThat(rulesRepository.count()).isEqualTo(0)
        assertThat(ruleTypesRepository.count()).isEqualTo(2) // deletes don't propagate
    }


    @Test
    fun `save multiple rules separately`() {
        // given
        val ruleType1 = RuleType(name = "name1", paramsCount = 3)
        val ruleType2 = RuleType(name = "name2", paramsCount = 34)
        val rule1 = Rule(ruleType = ruleType1, parameters = "some parameters")
        val rule2 = Rule(ruleType = ruleType2, parameters = "some parameters2")

        // when
        ruleTypesRepository.save(ruleType1)
        ruleTypesRepository.save(ruleType2)
        rulesRepository.save(rule1)
        rulesRepository.save(rule2)
        val allSavedRules = rulesRepository.findAll()

        // then
        assertThat(allSavedRules.count()).isEqualTo(2)
        val savedRule1 = allSavedRules[0]
        assertThat(savedRule1.id).isEqualTo("1")
        assertThat(savedRule1.parameters).isEqualTo("some parameters")
        assertThat(savedRule1.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(savedRule1.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        val savedRule2 = allSavedRules[1]
        assertThat(savedRule2.id).isEqualTo("2")
        assertThat(savedRule2.parameters).isEqualTo("some parameters2")
        assertThat(savedRule2.createdAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
        assertThat(savedRule2.updatedAt).isCloseToUtcNow(TemporalUnitWithinOffset(1, ChronoUnit.SECONDS))
    }

}
