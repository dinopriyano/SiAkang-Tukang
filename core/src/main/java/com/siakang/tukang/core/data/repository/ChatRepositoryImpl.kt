package com.siakang.tukang.core.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.siakang.tukang.core.domain.model.Chat
import com.siakang.tukang.core.domain.model.Tukang
import com.siakang.tukang.core.domain.model.User
import com.siakang.tukang.core.domain.model.WhoChat
import com.siakang.tukang.core.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val chatRef: CollectionReference,
    private val whoChatRef: CollectionReference
) : ChatRepository {
    override suspend fun getWhoChat(userId: String): Query {
        return whoChatRef.whereArrayContains("usersId", userId).orderBy("chatDate")
    }

    override suspend fun getChat(friendId: String, userId: String): Query {
        val userList = listOf(friendId, userId).sorted()
        return chatRef.whereEqualTo("userOne", userList[0]).whereEqualTo("userTwo", userList[1])
            .orderBy("chatDate")
    }

    override suspend fun sendChat(
        tukang: Tukang,
        user: User,
        message: String
    ): Task<Unit> {
        val id = chatRef.document().id
        val userList = listOf(tukang.id, user.id).sorted()
        val senderIndex = userList.indexOf(tukang.id)
        val usersName = mutableListOf<String>()
        val usersProfile = mutableListOf<String>()
        val currentTimestamp = Timestamp.now()
        val chat = Chat(
            chat = message,
            chatDate = currentTimestamp,
            id = id,
            senderId = tukang.id,
            userOne = userList[0],
            userTwo = userList[1]
        )

        if (senderIndex == 0) {
            usersName.addAll(listOf(tukang.name, user.name))
            usersProfile.addAll(listOf(tukang.photoProfile.orEmpty(), user.photo_profile.orEmpty()))
        } else {
            usersName.addAll(listOf(user.name, tukang.name))
            usersProfile.addAll(listOf(user.photo_profile.orEmpty(), tukang.photoProfile.orEmpty()))
        }

        return chatRef.document(id)
            .set(chat)
            .continueWith {
                whoChatRef.document(
                    userList.joinToString("")
                ).set(
                    WhoChat(
                        chatDate = currentTimestamp,
                        id = userList.joinToString(""),
                        lastChat = message,
                        usersId = userList,
                        usersName = usersName,
                        usersProfile = usersProfile
                    )
                )
            }
    }
}