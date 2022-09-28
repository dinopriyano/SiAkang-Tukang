package com.siakang.tukang.presentation.screen.dashboard.chat

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.WhoChat
import com.siakang.tukang.core.domain.usecase.ChatUseCase
import com.siakang.tukang.core.domain.usecase.DataStoreUseCase
import com.siakang.tukang.core.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val dataStoreUseCase: DataStoreUseCase
): BaseViewModel() {

    var whoChatResponse by mutableStateOf<List<WhoChat>>(emptyList())
    var userId by mutableStateOf<String>("")

    init {
        getUserId()
        getWhoChat()
    }

    fun getUserId() {
        viewModelScope.launch {
            userId = dataStoreUseCase.getUid().first()
        }
    }

    fun getWhoChat(){
        viewModelScope.launch {
            chatUseCase.getWhoChat(userId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        whoChatResponse = result.value
                    }
                    else -> Unit
                }
            }
        }
    }

}