package com.github.juanmougan.engine.processors

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition
import com.github.juanmougan.engine.api.Rule
import com.github.juanmougan.engine.com.github.juanmougan.engine.processors.RuleProcessor
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class RuleProcessorTest : StringSpec({

    val processor = RuleProcessor()

    // TODO can I use something like Junit's @Nested here
    // to separate Conditions and Actions tests?

    "should throw an exception if the Rule has no Conditions" {
        // Given a Rule with no Conditions
        val clazz = RuleWithZeroConditions::class.java
        // When process rule, then an exception is thrown
        val exception = shouldThrow<IllegalArgumentException> {
            processor.processRule(clazz)
        }
        exception.message shouldBe "Only one Condition method is allowed"
    }

    "should throw an exception if the Rule has more than one Condition" {
        // Given a Rule with two Conditions
        val clazz = RuleWithTwoConditions::class.java
        // When process rule, then an exception is thrown
        val exception = shouldThrow<IllegalArgumentException> {
            processor.processRule(clazz)
        }
        exception.message shouldBe "Only one Condition method is allowed"
    }

    "should throw an exception if the Rule a single Condition which doesn't return a Boolean value" {
        // Given a Rule with a single Condition, which doesn't return a Boolean
        val clazz = RuleWithNonBooleanCondition::class.java
        // When process rule, then an exception is thrown
        val exception = shouldThrow<IllegalArgumentException> {
            processor.processRule(clazz)
        }
        exception.message shouldBe "Method annotated with ${Condition::class.java.simpleName} must return a Boolean value"
    }

    "should throw an exception if the Rule has no Actions" {
        // Given a Rule with no Actions
        val clazz = RuleWithNoActions::class.java
        // When process rule, then an exception is thrown
        val exception = shouldThrow<IllegalArgumentException> {
            processor.processRule(clazz)
        }
        exception.message shouldBe "Only one Action method is allowed"
    }

    "should throw an exception if the Rule has more than one Action" {
        // Given a Rule with two Actions
        val clazz = RuleWithTwoActions::class.java
        // When process rule, then an exception is thrown
        val exception = shouldThrow<IllegalArgumentException> {
            processor.processRule(clazz)
        }
        exception.message shouldBe "Only one Action method is allowed"
    }
})

@Rule
class RuleWithZeroConditions {
    @Action
    fun dummyAction() {
    }
}

@Rule
class RuleWithTwoConditions {
    @Condition
    fun firstCondition(): Boolean {
        return true
    }

    @Condition
    fun twoCondition(): Boolean {
        return false
    }

    @Action
    fun dummyAction() {
    }
}

@Rule
class RuleWithNonBooleanCondition {
    @Condition
    fun dummyConditionNoBoolean() {
    }

    @Action
    fun dummyAction() {
    }
}

@Rule
class RuleWithNoActions {
    @Condition
    fun dummyCondition(): Boolean {
        return true
    }
}

@Rule
class RuleWithTwoActions {
    @Condition
    fun dummyAction(): Boolean {
        return true
    }

    @Action
    fun dummyActionOne() {}

    @Action
    fun dummyActionTwo() {}
}
