package com.siakang.tukang.presentation.screen.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.siakang.tukang.R
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Chat
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.presentation.component.chat_item.ReceiverEndChatItem
import com.siakang.tukang.presentation.component.chat_item.SenderEndChatItem
import com.siakang.tukang.presentation.component.toolbar.ChatToolbar
import com.siakang.tukang.presentation.theme.*

@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@Composable
fun ChatScreen(
    navController: NavHostController,
    friendId: String?,
    viewModel: ChatViewModel = hiltViewModel()
) {

    viewModel.getChat(friendId ?: "")
    viewModel.getCustomer(friendId ?: "")
    var chats by remember {
        mutableStateOf(emptyList<Chat>())
    }
    val chatsResponse = viewModel.chatResponse
    val customer = viewModel.customerResponse
    val userId = viewModel.userId
    val chatMessage = viewModel.chatMessage

    val listState = rememberLazyListState()

    LaunchedEffect(chatsResponse) {
        when (chatsResponse) {
            is Resource.Success -> {
                listState.scrollToItem(chatsResponse.value.size)
                chats = chatsResponse.value
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            ChatToolbar(navController, customer)
        },
        bottomBar = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                )
            ) {
                Row(
                    modifier = Modifier.padding(25.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 15.dp)
                            .padding(vertical = 1.dp),
                        singleLine = true,
                        value = chatMessage,
                        shape = RoundedCornerShape(50.dp),
                        onValueChange = viewModel::setMessage,
                        colors = TextFieldDefaults.textFieldColors(
                            placeholderColor = Placeholder,
                            textColor = Label,
                            containerColor = ReceiverChat,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        placeholder = {
                            Text(
                                fontSize = 14.sp,
                                text = "Ketik disini..."
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            viewModel.sendMessage()
                            viewModel.setMessage("")
                        },
                        modifier = Modifier
                            .size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_send),
                            contentDescription = null,
                            tint = Primary
                        )
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrey)
                .padding(padding)
        ) {
            chats.forEachIndexed { index, chat ->
                item {
                    if (chat.senderId == userId) {
                        SenderEndChatItem(
                            message = chat.chat, modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 15.dp, top = 15.dp)
                        )
                    } else {
                        ReceiverEndChatItem(
                            message = chat.chat, modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 15.dp, top = 15.dp)
                        )
                    }
                }
            }
        }
    }
}