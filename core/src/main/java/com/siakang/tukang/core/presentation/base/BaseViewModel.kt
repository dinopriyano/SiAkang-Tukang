package com.siakang.tukang.core.presentation.base

import androidx.lifecycle.ViewModel
import com.siakang.tukang.core.data.model.Resource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

abstract class BaseViewModel: ViewModel() {

    protected suspend fun <T> Flow<Resource<T>>.runFlow(stateVariable: MutableStateFlow<Resource<T>>) {
        stateVariable.value = Resource.Loading
        collect { result ->
            stateVariable.value = result
        }
    }

    protected suspend fun <T> Flow<Resource<T>>.runFlow(stateVariable: (Resource<T>) -> Unit) {
        stateVariable.invoke(Resource.Loading)
        collect { result ->
            stateVariable.invoke(result)
        }
    }

    protected suspend fun <T> Flow<Resource<T>>.runFlow(stateVariable: Channel<Resource<T>>) {
        stateVariable.send(Resource.Loading)
        collect { result ->
            stateVariable.send(result)
        }
    }

}