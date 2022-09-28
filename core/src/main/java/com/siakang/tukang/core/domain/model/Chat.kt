package com.siakang.tukang.core.domain.model

import com.google.firebase.Timestamp

data class Chat(
    val chat: String = "",
    val chatDate: Timestamp = Timestamp.now(),
    val id: String = "",
    val senderId: String = "",
    val userOne: String = "",
    val userTwo: String = ""
)