package com.siakang.tukang.core.domain.model

import com.google.firebase.Timestamp

data class WhoChat(
    val chatDate: Timestamp = Timestamp.now(),
    val id: String = "",
    val lastChat: String = "",
    val usersId: List<String> = listOf(),
    val usersName: List<String> = listOf(),
    val usersProfile: List<String> = listOf()
)