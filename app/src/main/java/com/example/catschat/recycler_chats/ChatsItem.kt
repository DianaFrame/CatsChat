package com.example.catschat.recycler_chats

import com.example.catschat.models.Status


data class ChatsItem(
    val id: String? = null,
    val name: String? = null,
    val status: Status? = null,
    val avatarNumber: Int? = null,
    val lastMessage: String? = null,
    val timeLastMessage: String? = null,
)
