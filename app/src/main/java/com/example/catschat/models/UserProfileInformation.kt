package com.example.catschat.models

import com.example.catschat.recycler_chats.ChatsItem

data class UserProfileInformation(
    val id: String? = null,
    val avatarNumber: Int? = null,
    val name: String? = null,
    val phone: String? = null,
    val birthday: String? = null,
    val email: String? = null,
    val password: String? = null,
    val status: Status? = null,
)
