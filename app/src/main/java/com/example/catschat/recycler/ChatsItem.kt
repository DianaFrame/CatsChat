package com.example.catschat.recycler

import com.example.domain.Status

data class ChatsItem(
    val id: ULong,
    val name: String,
    val status: Status,
    val avatar: String,
    val lastMessage: String,
)
