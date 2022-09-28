package com.siakang.tukang.presentation.screen.image_detail


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.component.zoomable_image_view.ZoomableImage

@ExperimentalMaterial3Api
@Composable
fun ImageDetailScreen(
    navController: NavHostController,
    imageUrl: String?
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Image Detail", navController = navController)
        }
    ) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            Text(text = imageUrl.orEmpty())
//        }
        ZoomableImage(image = imageUrl.orEmpty().toUri())
    }
}