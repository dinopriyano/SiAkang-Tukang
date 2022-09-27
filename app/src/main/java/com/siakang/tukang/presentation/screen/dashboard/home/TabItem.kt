package com.siakang.tukang.presentation.screen.dashboard.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.siakang.tukang.presentation.screen.dashboard.home.tab.active.ActiveScreen
import com.siakang.tukang.presentation.screen.dashboard.home.tab.offer.OfferScreen

sealed class TabItem(var label: String, var screen: @Composable (NavHostController) -> Unit) {
    object Offer: TabItem("Penawaran", { OfferScreen(it)})
    object Active: TabItem("Aktif", { ActiveScreen(it)})

    object Items {
        val items = listOf(
            Offer, Active
        )
    }
}
