package com.siakang.tukang.presentation.screen.pending_verification

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.siakang.tukang.R
import com.siakang.tukang.presentation.component.empty.EmptyState
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun PendingVerificationScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Pendaftaran SiAkang", navController = navController)
        }
    ) {
        EmptyState(
            imageResource = R.drawable.verification,
            isAnimation = false,
            title = "Pendaftaran anda dalam proses verifikasi",
            description = "Mohon untuk menunggu proses pendaftaran anda ya! Pendaftaran anda masih dalam proses verifikasi oleh admin kami!",
            textButton = "Cek Status Pendaftaran"
        ) {
            navController.navigate("registration_detail")
        }
    }
}