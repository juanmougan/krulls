package com.github.juanmougan.engine

import com.github.juanmougan.engine.api.RuleEngine
import com.github.juanmougan.engine.com.github.juanmougan.engine.processors.RuleProcessor
import io.github.classgraph.ClassGraph


// TODO this is only meant for manual testing
// delete once this becomes a real library
fun main(args: Array<String>){
    val engine = RuleEngine(classGraph = ClassGraph(), rulesProcessor = RuleProcessor())
    engine.evaluateRules("com.github.juanmougan.engine.examples")
}
