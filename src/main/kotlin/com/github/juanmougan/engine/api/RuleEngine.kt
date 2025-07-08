package com.github.juanmougan.engine.api

import com.github.juanmougan.engine.com.github.juanmougan.engine.validators.getValidAnnotatedMethod
import io.github.classgraph.ClassGraph

class RuleEngine {

    // TODO also return the value of the execution. I'm gonna need generics for that
    // TODO how can I provide parameters? With something like the `Facts`?
    fun evaluateRules(scanPackage: String) {
        // TODO more refactor? E.g. inject this
        val scanResult = ClassGraph().enableClassInfo().enableAnnotationInfo().acceptPackages(scanPackage).scan()
        scanResult.use { result ->
            result.getClassesWithAnnotation(Rule::class.java.getName()).forEach { classInfo ->
                val clazz = classInfo.loadClass()
                if (clazz.isAnnotationPresent(Rule::class.java)) {      // TODO double checking here
                    processRule(clazz)
                }
            }
        }
    }

    fun processRule(ruleClass: Class<*>) {
        val clazzInstance = ruleClass.getDeclaredConstructor().newInstance()
        val ruleMethod = getValidAnnotatedMethod(
            clazz = ruleClass,
            annotation = Condition::class.java
        ).also { method -> method.returnType == Boolean::class.java }
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
