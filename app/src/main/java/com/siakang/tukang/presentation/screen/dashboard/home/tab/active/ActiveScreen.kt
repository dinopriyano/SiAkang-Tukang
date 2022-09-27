package com.siakang.tukang.presentation.screen.dashboard.home.tab.active

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.domain.model.Order
import com.siakang.tukang.presentation.component.order_item.OrderActiveItem
import com.siakang.tukang.presentation.component.order_item.OrderOfferItem
import com.siakang.tukang.presentation.screen.dashboard.home.HomeViewModel
import com.siakang.tukang.presentation.theme.Primary

@Composable
fun ActiveScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.getOrderActive()
    val orderResponse by viewModel.orderActiveResponse.collectAsState(initial = Resource.Empty)
    var orders by remember {
        mutableStateOf(listOf<Order>())
    }

    LaunchedEffect(orderResponse) {
        Log.i("kontol", "ActiveScreen: $orderResponse")
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

                },
                onChatClick = { order ->

                }
            )
        }
    }
}