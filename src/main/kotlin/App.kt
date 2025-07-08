package com.github.juanmougan.engine

import com.github.juanmougan.engine.api.RuleEngine


// TODO this is only meant for manual testing
// delete once this becomes a real library
fun main(args: Array<String>){
    val engine = RuleEngine()
    engine.evaluateRules("com.github.juanmougan.engine.examples")
}
