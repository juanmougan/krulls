package com.github.juanmougan.engine.validators

import com.github.juanmougan.engine.api.RuleEngine
import com.github.juanmougan.engine.com.github.juanmougan.engine.processors.RuleProcessor
import com.github.juanmougan.engine.samples.withclasses.ValidRule
import io.github.classgraph.ClassGraph
import io.kotest.core.spec.style.StringSpec
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

const val PACKAGE_WITH_CLASSES = "com.github.juanmougan.engine.samples.withclasses"

/**
 * Sort of hybrid test, since mocking `ClassGraph` seemed a bit complex.
 */
class RuleEngineTests : StringSpec({

    "only classes in given package with the @Rule annotation should be scanned" {
        // Given a package with classes to scan and others to skip
        val processor = mockk<RuleProcessor>()
        val engine = RuleEngine(classGraph = ClassGraph(), rulesProcessor = processor)
        every { processor.processRule(any()) } just Runs

        // When process the rules
        engine.evaluateRules(PACKAGE_WITH_CLASSES)

        // Then only properly annotated rules are scanned
        val classToProcess = ValidRule::class.java
        verify(exactly = 1) { processor.processRule(classToProcess) }
        confirmVerified(processor)
    }
})
