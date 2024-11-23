package io.greedy.goblin.api.v1.mappers.exceptions

import kotlin.reflect.KClass

class UnknownRequestClass(
    clazz: KClass<*>,
) : RuntimeException("Class ${clazz.simpleName} cannot be mapped to GameContext")
