package com.github.juanmougan.engine.validators

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition
import com.github.juanmougan.engine.api.Rule
import com.github.juanmougan.engine.api.RuleEngine
import io.github.classgraph.ClassGraph
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain

class RuleEngineTests : StringSpec({

    "only classes in given package should be scanned" {
        val scanned = ClassGraph().enableClassInfo().acceptPackages("com.example.rules.testcases")
            .scan().allClasses.map { it.name }

        assert(scanned.all { it.startsWith("com.example.rules.testcases") })
    }

    "only classes annotated with @Rule should be picked" {
        val result = ClassGraph().enableAllInfo().acceptPackages("com.example.rules.testcases").scan()

        val rules = result.getClassesWithAnnotation(Rule::class.qualifiedName)
        assert(rules.none { it.name.contains("NotARule") })
    }

    // TODO parametrize these tests, if possible
    "fail if rule has more than one @Condition" {
        val engine = RuleEngine()
        val ex = shouldThrow<IllegalArgumentException> {
            engine.evaluateRules("com.example.rules.testcases.multipleconditions")
        }
        ex.message shouldContain "Only one ${Condition::class.java.simpleName} method is allowed"
    }

    "fail if rule has more than one @Action" {
        val engine = RuleEngine()
        val ex = shouldThrow<IllegalArgumentException> {
            engine.evaluateRules("com.example.rules.testcases.multipleconditions")
        }
        ex.message shouldContain "Only one ${Action::class.java.simpleName} method is allowed"
    }
})
