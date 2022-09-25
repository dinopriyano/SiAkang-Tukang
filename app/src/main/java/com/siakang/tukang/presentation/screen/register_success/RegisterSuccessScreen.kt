package com.siakang.tukang.presentation.screen.register_success

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.siakang.tukang.presentation.component.empty.EmptyState
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.R

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun SuccessRegisterScreen(
    navController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "", navController = navController)
        }
    ) {
        EmptyState(
            imageResource = R.raw.success,
            isAnimation = true,
            title = "Berhasil mendaftar",
            description = "Yeay, kamu sudah berhasil membuat akun untuk menjadi Akang. Silahkan login menggunakan akun yang telah didaftarkan.",
            textButton = "Login"
        ) {
            navController.navigate("login") {
                popUpTo(0)
            }
        }
    }
}