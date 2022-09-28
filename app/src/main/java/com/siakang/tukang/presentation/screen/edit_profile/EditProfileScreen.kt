package com.siakang.tukang.presentation.screen.edit_profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.siakang.tukang.presentation.component.toolbar.DefaultToolbar
import com.siakang.tukang.presentation.screen.dashboard.profile.ProfileViewModel

@ExperimentalMaterial3Api
@Composable
fun EditProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            DefaultToolbar(title = "Edit Data Pribadi", navController = navController)
        }
    ) {

    }
}