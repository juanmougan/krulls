package com.github.juanmougan.engine.com.github.juanmougan.engine.validators

import java.lang.reflect.Method

fun <T : Annotation> getValidAnnotatedMethod(clazz: Class<*>, annotation: Class<T>): Method {
    val methods = clazz.methods.filter { method -> method.isAnnotationPresent(annotation) }
    require(methods.size == 1) { "Only one ${annotation.simpleName} method is allowed" }
    return methods[0]
}

fun <T : Annotation> getValidAnnotatedMethodReturningBoolean(clazz: Class<*>, annotation: Class<T>): Method {
    return getValidAnnotatedMethod(
        clazz = clazz,
        annotation = annotation
    ).also { method -> require(method.returnType == Boolean::class.java) { "Method annotated with ${annotation.simpleName} must return a Boolean value" } }
}

fun Method.validateCanAccess(instance: Any) {
    if (!this.canAccess(instance)) {
        throw IllegalStateException("Can't access method: ${this.name}, please check its visibility")
    }
}
