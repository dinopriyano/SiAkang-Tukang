package com.siakang.tukang.presentation.screen.dashboard.home.tab.finish

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.presentation.component.order_item.OrderActiveItem
import com.siakang.tukang.presentation.screen.dashboard.home.HomeViewModel

@Composable
fun FinishScreen(
    parentNavController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.getOrderFinish()
    val orderResponse by viewModel.orderFinishResponse.collectAsState(initial = Resource.Empty)
    var orders by remember {
        mutableStateOf(listOf<Order>())
    }

    LaunchedEffect(orderResponse) {
        when (orderResponse) {
            is Resource.Success -> {
                orders = (orderResponse as Resource.Success).value
            }
            else -> Unit
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(orders) { order ->
            OrderActiveItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 20.dp),
                order = order,
                onDetailClick = { order ->
                    parentNavController.navigate("offer_detail/${order.id}")
                },
                onChatClick = { order ->
                    parentNavController.navigate("chat_detail/${order.userId}")
                }
            )
        }
    }
}