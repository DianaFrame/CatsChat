package com.example.catschat.recycler_chats

import com.example.catschat.recycler_users.UserItem

interface ChatListener {
    fun onClick(userItem: UserItem)
}