package com.siakang.tukang.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val userUseCase: UserUseCase
): BaseViewModel() {

    private val _destination = Channel<String>()
    val destination: Flow<String> get() = _destination.receiveAsFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        getUid()
    }

    private fun getUid() {
        viewModelScope.launch {
            dataStoreUseCase.getUid().collectLatest {
                if(it.isEmpty()) {
                    _isLoading.value = false
                    _destination.trySend("login")
                }
                else {
                    getUser(it)
                }
            }
        }
    }

    private fun getUser(uid: String) {
        viewModelScope.launch {
            userUseCase.getUser(uid).collectLatest {
                when(it) {
                    is Resource.Success -> {
                        it.value?.let { tukang ->
                            if(tukang.areDataComplete) {
                                _destination.trySend("dashboard")
                            }
                            else {
                                _destination.trySend("personal_information")
                            }
                        }
                    }
                    else -> Unit
                }
                _isLoading.value = false
            }
        }
    }

}