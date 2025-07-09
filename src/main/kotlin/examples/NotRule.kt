package com.github.juanmougan.engine.examples

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition

class NotRule {
    @Condition
    fun helloCondition(): Boolean {
        println("Not a condition")
        return true
    }

    @Action
    fun helloAction() {
        println("Not an action")
    }
}
