package com.github.juanmougan.engine.samples.withclasses

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition
import com.github.juanmougan.engine.api.Rule

@Rule
class ValidRule {
    @Condition
    fun validCondition(): Boolean {
        return true
    }

    @Action
    fun validAction() {
        println("valid action")
    }
}
