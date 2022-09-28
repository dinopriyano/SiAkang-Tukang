package com.siakang.tukang.presentation.screen.dashboard.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel() {

    var user by mutableStateOf(Tukang())

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            userUseCase.getUser(dataStoreUseCase.getUid().first()).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        user = result.value ?: Tukang()
                    }
                    else -> Unit
                }
            }
        }
    }

    fun clearLoginSession() {
        viewModelScope.launch {
            dataStoreUseCase.clear()
        }
    }

}