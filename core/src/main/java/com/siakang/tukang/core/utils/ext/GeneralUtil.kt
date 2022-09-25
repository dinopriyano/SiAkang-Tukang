package com.siakang.tukang.core.utils.ext

import kotlin.reflect.full.memberProperties

inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
    return T::class.memberProperties.associate { it.name to it.get(this) }
}