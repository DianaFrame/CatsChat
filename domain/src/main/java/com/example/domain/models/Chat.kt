package com.example.domain.models

import com.example.domain.Status

data class Chat(
    val id: ULong,
    val name: String,
    val status: Status,
    val avatar: String,
    val lastMessage: String,
)
