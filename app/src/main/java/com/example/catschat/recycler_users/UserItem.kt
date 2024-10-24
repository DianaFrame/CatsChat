package com.example.catschat.recycler_users

import com.example.catschat.models.Status

data class UserItem(
    val id: String? = null,
    val name: String? = null,
    val avatarNumber: Int? = null,
    val status: Status? = null,
)
