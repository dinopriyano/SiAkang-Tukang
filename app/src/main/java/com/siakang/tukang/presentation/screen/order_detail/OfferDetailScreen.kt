package com.siakang.tukang.presentation.screen.order_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.core.data.model.Resource
import com.siakang.tukang.core.utils.ext.toDateString
import com.siakang.tukang.presentation.component.file_preview.FilePreview
import com.siakang.tukang.presentation.component.loading_button.LoadingButton
import com.siakang.tukang.presentation.component.loading_button.OutlinedLoadingButton
import com.siakang.tukang.presentation.component.title_description.TitleDescription
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.theme.*

@ExperimentalMaterial3Api
@Composable
fun OfferDetailScreen(
    navController: NavHostController,
    orderId: String?,
    viewModel: OfferDetailViewModel = hiltViewModel()
) {

    viewModel.getOrderDetail(orderId ?: "")
    val orderDetail = viewModel.orderDetail
    val acceptOrder = viewModel.acceptResponse
    val completeOrder = viewModel.completeOrderResponse

    LaunchedEffect(acceptOrder) {
        when(acceptOrder) {
            is Resource.Success -> {
                navController.popBackStack()
            }
            else -> Unit
        }
    }

    LaunchedEffect(completeOrder) {
        when(completeOrder) {
            is Resource.Success -> {
                navController.popBackStack()
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Detail Penawaran", navController = navController)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            TitleDescription(
                title = "Perbaikan ${orderDetail.category} yang diperlukan",
                desc = orderDetail.subCategory,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 40.dp),
                titleStyle = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    color = Dark
                ),
                descStyle = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 14.sp,
                    color = Placeholder
                ),
                spacerHeight = 8.dp
            )
            TitleDescription(
                title = "Detail Perbaikan",
                desc = orderDetail.detail,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 24.dp),
                titleStyle = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    color = Dark
                ),
                descStyle = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 14.sp,
                    color = Placeholder
                ),
                spacerHeight = 8.dp
            )
            TitleDescription(
                title = "Tanggal",
                desc = orderDetail.requestDate.toDateString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 24.dp),
                titleStyle = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    color = Dark
                ),
                descStyle = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 14.sp,
                    color = Placeholder
                ),
                spacerHeight = 8.dp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(30.dp, 24.dp, 30.dp, 0.dp),
                text = "File",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    color = Dark
                )
            )
            orderDetail.file.forEachIndexed { index, fileUri ->
                FilePreview(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 8.dp, 30.dp, 0.dp),
                    imageColor = Secondary,
                    imageUri = fileUri.toUri(),
                    imageName = "Foto Masalah ${index + 1}"
                )
            }
            TitleDescription(
                title = "Catatan Tambahan",
                desc = orderDetail.note,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 24.dp),
                titleStyle = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 14.sp,
                    color = Dark
                ),
                descStyle = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 14.sp,
                    color = Placeholder
                ),
                spacerHeight = 8.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = 40.dp)
            ) {
                orderDetail.tukangId?.let {
                    if(it.isNotEmpty()) {
                        LoadingButton(
                            text = "Sudah Selesai",
                            colors = Primary,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            onClick = {
                                orderId?.let {
                                    viewModel.completeOrder(it)
                                }
                            },
                            enabled = true,
                            loading = completeOrder is Resource.Loading
                        )
                    }
                    else {
                        OutlinedLoadingButton(
                            text = "Tolak",
                            colors = Reject,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            onClick = {

                            },
                            enabled = true,
                            loading = false
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        LoadingButton(
                            text = "Terima",
                            colors = Accept,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            onClick = {
                                orderId?.let {
                                    viewModel.acceptOrder(it)
                                }
                            },
                            enabled = true,
                            loading = acceptOrder is Resource.Loading
                        )
                    }
                }
            }
        }
    }
}