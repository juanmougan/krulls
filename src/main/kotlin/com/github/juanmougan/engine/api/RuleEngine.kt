package com.github.juanmougan.engine.api

import com.github.juanmougan.engine.com.github.juanmougan.engine.processors.RuleProcessor
import io.github.classgraph.ClassGraph

class RuleEngine(private val classGraph: ClassGraph, private val rulesProcessor: RuleProcessor) {

    // TODO also return the value of the execution. I'm gonna need generics for that
    // TODO how can I provide parameters? With something like the `Facts`?
    fun evaluateRules(scanPackage: String) {
        val scanResult = classGraph.enableClassInfo().enableAnnotationInfo().acceptPackages(scanPackage).scan()
        scanResult.getClassesWithAnnotation(Rule::class.java.getName()).forEach { classInfo ->
            val clazz = classInfo.loadClass()
            rulesProcessor.processRule(clazz)
        }
    }
}
