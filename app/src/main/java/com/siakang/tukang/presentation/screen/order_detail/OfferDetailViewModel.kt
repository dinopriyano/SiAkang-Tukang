package com.siakang.tukang.presentation.screen.order_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.OrderUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDetailViewModel @Inject constructor(
    private val orderUseCase: OrderUseCase,
    private val userUseCase: UserUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel() {

    var orderDetail by mutableStateOf(Order())
    var tukang by mutableStateOf(Tukang())
    var acceptResponse by mutableStateOf<Resource<Unit>>(Resource.Empty)
    var completeOrderResponse by mutableStateOf<Resource<Unit>>(Resource.Empty)

    init {
        getTukang()
    }

    fun getOrderDetail(orderId: String) {
        viewModelScope.launch {
            orderUseCase.getOrderDetail(orderId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        orderDetail = result.value
                    }
                    else -> Unit
                }
            }
        }
    }

    fun getTukang() {
        viewModelScope.launch {
            userUseCase.getUser(dataStoreUseCase.getUid().first()).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        tukang = result.value ?: Tukang()
                    }
                    else -> Unit
                }
            }
        }
    }

    fun acceptOrder(orderId: String) {
        viewModelScope.launch {
            acceptResponse = Resource.Loading
            orderUseCase.acceptOrder(orderId, tukang).collect { result ->
                acceptResponse = result
            }
        }
    }

    fun completeOrder(orderId: String) {
        viewModelScope.launch {
            completeOrderResponse = Resource.Loading
            orderUseCase.completeOrder(orderId).collect { result ->
                completeOrderResponse = result
            }
        }
    }

}