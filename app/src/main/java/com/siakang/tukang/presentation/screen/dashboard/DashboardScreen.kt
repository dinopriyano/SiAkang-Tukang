package com.siakang.tukang.presentation.screen.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.siakang.tukang.presentation.screen.dashboard.Screens.Items.items
import com.siakang.tukang.presentation.theme.Primary

@ExperimentalPagerApi
@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun DashboardScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                elevation = 16.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = "icon can't display",
                                tint = if (currentRoute == it.route) Primary
                                else com.siakang.tukang.presentation.theme.Icon
                            )
                        },
                        selected = currentRoute == it.route,
                        onClick = {
                            if (currentRoute != it.route) {
                                navController.graph.startDestinationRoute?.let { item ->
                                    navController.popBackStack(
                                        item, false
                                    )
                                }
                            }
                            if (currentRoute != it.route) {
                                navController.navigate(it.route) {
                                    launchSingleTop = true
                                }
                            }
                        },
                        alwaysShowLabel = false,
                        selectedContentColor = Primary,
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            DashboardScreenController(navController = navController)
        }
    }
}