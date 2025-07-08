package com.github.juanmougan.engine.com.github.juanmougan.engine.validators

import java.lang.reflect.Method

fun <T : Annotation> getValidAnnotatedMethod(clazz: Class<*>, annotation: Class<T>): Method {
    val methods = clazz.methods.filter { method -> method.isAnnotationPresent(annotation) }
    require(methods.size == 1) { "Only one ${annotation.simpleName} method is allowed" }
    return methods[0]
}
