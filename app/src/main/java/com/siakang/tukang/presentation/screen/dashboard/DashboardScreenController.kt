package com.siakang.tukang.presentation.screen.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.siakang.tukang.presentation.screen.dashboard.chat.ChatScreen
import com.siakang.tukang.presentation.screen.dashboard.home.HomeScreen
import com.siakang.tukang.presentation.screen.dashboard.profile.ProfileScreen

@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
@Composable
fun DashboardScreenController(
    navController: NavHostController,
    parentNavController: NavHostController
) {
    NavHost(navController = navController, startDestination = "home") {
        composable(
            route = "home"
        ) {
            HomeScreen(parentNavController)
        }

        composable(
            route = "chat"
        ) {
            ChatScreen(parentNavController)
        }

        composable(
            route = "profile"
        ) {
            ProfileScreen(parentNavController)
        }
    }
}