package com.github.juanmougan.engine.com.github.juanmougan.engine.processors

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition
import com.github.juanmougan.engine.com.github.juanmougan.engine.validators.getValidAnnotatedMethod
import com.github.juanmougan.engine.com.github.juanmougan.engine.validators.getValidAnnotatedMethodReturningBoolean

class RuleProcessor {
    fun processRule(ruleClass: Class<*>) {
        val clazzInstance = ruleClass.getDeclaredConstructor().newInstance()
        val ruleMethod = getValidAnnotatedMethodReturningBoolean(
            clazz = ruleClass, annotation = Condition::class.java
        )
        val actionMethod = getValidAnnotatedMethod(clazz = ruleClass, annotation = Action::class.java)
        // TODO refactor here - this is complex to read
        if (ruleMethod.canAccess(clazzInstance)) {
            val ruleEvaluationResult = ruleMethod.invoke(clazzInstance) == true
            if (ruleEvaluationResult) {
                if (actionMethod.canAccess(clazzInstance)) {
                    actionMethod.invoke(clazzInstance)
                } else {
                    throw IllegalStateException("Can't access action method: ${actionMethod.name}, please check its visibility")
                }
            }
        } else {
            throw IllegalStateException("Can't access condition method: ${ruleMethod.name}, please check its visibility")
        }
    }
}
