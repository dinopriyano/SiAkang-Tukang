package com.siakang.tukang.presentation.screen.dashboard.home

import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.OrderUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val orderUseCase: OrderUseCase
): BaseViewModel() {

    private val _userResponse = Channel<Resource<Tukang?>>(Channel.CONFLATED)
    val userResponse: Flow<Resource<Tukang?>> get() = _userResponse.receiveAsFlow()

    private val _orderOfferResponse = Channel<Resource<List<Order>>>(Channel.CONFLATED)
    val orderOfferResponse: Flow<Resource<List<Order>>> get() = _orderOfferResponse.receiveAsFlow()
    private val _orderActiveResponse = Channel<Resource<List<Order>>>(Channel.CONFLATED)
    val orderActiveResponse: Flow<Resource<List<Order>>> get() = _orderActiveResponse.receiveAsFlow()

    fun getUser() {
        viewModelScope.launch {
            _userResponse.trySend(Resource.Loading)
            userUseCase.getUser(dataStoreUseCase.getUid().first()).collect { result ->
                _userResponse.trySend(result)
            }
        }
    }

    fun getOrderOffer() {
        viewModelScope.launch {
            _orderOfferResponse.trySend(Resource.Loading)
            dataStoreUseCase.getSkills().collect { skills ->
                orderUseCase.getOfferOrder(skills).collect { result ->
                    _orderOfferResponse.trySend(result)
                }
            }
        }
    }

    fun getOrderActive() {
        viewModelScope.launch {
            _orderActiveResponse.trySend(Resource.Loading)
            orderUseCase.getActiveOrder(dataStoreUseCase.getUid().first()).collect { result ->
                _orderActiveResponse.trySend(result)
            }
        }
    }

    fun storeSkills(skills: List<String>) {
        viewModelScope.launch {
            dataStoreUseCase.storeSkills(skills)
        }
    }

}