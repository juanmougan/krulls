package com.github.juanmougan.engine.com.github.juanmougan.engine.processors

import com.github.juanmougan.engine.api.Action
import com.github.juanmougan.engine.api.Condition
import com.github.juanmougan.engine.com.github.juanmougan.engine.validators.getValidAnnotatedMethod
import com.github.juanmougan.engine.com.github.juanmougan.engine.validators.getValidAnnotatedMethodReturningBoolean
import com.github.juanmougan.engine.com.github.juanmougan.engine.validators.validateCanAccess

class RuleProcessor {
    fun processRule(ruleClass: Class<*>): Any? {
        val clazzInstance = ruleClass.getDeclaredConstructor().newInstance()
        val conditionMethod =
            getValidAnnotatedMethodReturningBoolean(
                clazz = ruleClass,
                annotation = Condition::class.java,
            )
        val actionMethod = getValidAnnotatedMethod(clazz = ruleClass, annotation = Action::class.java)

        conditionMethod.validateCanAccess(clazzInstance)
        actionMethod.validateCanAccess(clazzInstance)

        val ruleEvaluationResult = conditionMethod.invoke(clazzInstance) == true
        return if (ruleEvaluationResult) {
            // TODO can I check on runtime that this type matches `actionMethod.genericReturnType`?
            return actionMethod.invoke(clazzInstance)
        } else {
            null
        }
    }
}
