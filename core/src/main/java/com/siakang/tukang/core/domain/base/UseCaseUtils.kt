package com.siakang.tukang.core.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

fun <T>execute(
    context: CoroutineContext = Dispatchers.Default,
    block: suspend () -> T
): Flow<T> {
    return flow {
        val out = block.invoke()
        emit(out)
    }.flowOn(context)
}