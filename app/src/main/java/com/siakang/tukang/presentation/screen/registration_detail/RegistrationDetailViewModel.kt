package com.siakang.tukang.presentation.screen.registration_detail

import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationDetailViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel() {

    private val _userResponse = Channel<Resource<Tukang?>>(Channel.CONFLATED)
    val userResponse: Flow<Resource<Tukang?>> get() = _userResponse.receiveAsFlow()

    fun getUser() {
        viewModelScope.launch {
            _userResponse.trySend(Resource.Loading)
            userUseCase.getUser(dataStoreUseCase.getUid().first()).collect { result ->
                _userResponse.trySend(result)
            }
        }
    }

}