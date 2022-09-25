package com.siakang.tukang.presentation.screen.dashboard

import com.siakang.tukang.R

sealed class Screens(val route: String, var label: String, val icon: Int) {
    object Home: Screens("home", "Home", R.drawable.ic_home)
    object Chat: Screens("chat", "Chat", R.drawable.ic_chat)
    object Profile: Screens("profile", "Profile", R.drawable.ic_profile)

    object Items {
        val items = listOf(
            Home, Chat, Profile
        )
    }
}
