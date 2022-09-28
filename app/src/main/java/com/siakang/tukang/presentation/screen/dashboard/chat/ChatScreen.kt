package com.siakang.tukang.presentation.screen.dashboard.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.component.who_chat.WhoChatItem

@ExperimentalMaterial3Api
@Composable
fun ChatScreen(
    parentNavController: NavHostController,
    viewModel: ChatViewModel = hiltViewModel()
) {

    val whoChat = viewModel.whoChatResponse
    val userId = viewModel.userId

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Chat SiAkang", navController = parentNavController, hideBackArrow = true)
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)) {
            items(whoChat) { who ->
                WhoChatItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(top = 15.dp), whoChat = who, userId = userId
                ) {
                    parentNavController.navigate("chat_detail/$it")
                }
            }
        }
    }
}