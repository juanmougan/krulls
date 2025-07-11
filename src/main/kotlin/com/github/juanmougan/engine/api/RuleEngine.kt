package com.github.juanmougan.engine.api

import com.github.juanmougan.engine.com.github.juanmougan.engine.processors.RuleProcessor
import io.github.classgraph.ClassGraph

class RuleEngine(
    private val classGraph: ClassGraph,
    private val rulesProcessor: RuleProcessor,
) {
    // TODO how can I provide parameters? With something like the `Facts`?
    fun evaluateRules(scanPackage: String): List<Any?> {
        val scanResult =
            classGraph
                .enableClassInfo()
                .enableAnnotationInfo()
                .acceptPackages(scanPackage)
                .scan()
        return scanResult
            .getClassesWithAnnotation(Rule::class.java.getName())
            .map { classInfo ->
                classInfo.loadClass()
            }.map { rulesProcessor.processRule(it) }
            .toList()
    }
}
