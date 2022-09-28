package com.siakang.tukang.presentation.screen.chat

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Chat
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.core.domain.usecase.ChatUseCase
import com.siakang.tukang.core.domain.usecase.CustomerUseCase
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.domain.usecase.UserUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val customerUseCase: CustomerUseCase,
    private val userUseCase: UserUseCase
): BaseViewModel() {

    var chatResponse by mutableStateOf<Resource<List<Chat>>>(Resource.Empty)
    var sendChatResponse by mutableStateOf<Resource<Unit>>(Resource.Empty)
    var customerResponse by mutableStateOf(User())
    var meResponse by mutableStateOf(Tukang())
    var userId by mutableStateOf("")
    var chatMessage by mutableStateOf("")

    init {
        getUserId()
        getUser()
    }

    fun getUserId() {
        viewModelScope.launch {
            userId = dataStoreUseCase.getUid().first()
        }
    }

    fun getUser() {
        viewModelScope.launch {
            userUseCase.getUser(userId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        meResponse = result.value ?: Tukang()
                    }
                    else -> Unit
                }
            }
        }
    }

    fun getChat(friendId: String){
        viewModelScope.launch {
            chatUseCase.getChat(friendId, userId).collect { result ->
                chatResponse = result
            }
        }
    }

    fun getCustomer(friendId: String) {
        viewModelScope.launch {
            customerUseCase.getUser(friendId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        customerResponse = result.value ?: User()
                    }
                    else -> Unit
                }
            }
        }
    }

    fun setMessage(message: String) {
        chatMessage = message
    }

    fun sendMessage() {
        viewModelScope.launch {
            chatUseCase.sendChat(meResponse, customerResponse, chatMessage).collect { result ->
                sendChatResponse = result
            }
        }
    }

}