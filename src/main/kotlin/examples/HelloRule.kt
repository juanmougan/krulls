package com.github.juanmougan.engine.examples

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition
import com.github.juanmougan.engine.api.Rule

@Rule
class HelloRule {
    @Condition
    fun helloCondition(): Boolean {
        println("helloCondition")
        return true
    }

    @Action
    fun helloAction() {
        println("helloAction")
    }
}
