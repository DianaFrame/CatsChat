package com.example.catschat.recycler_messages

sealed interface MessageItem {
    data class CurrentUserMessageItem (
        val senderId: String? = null,
        val message: String? = null,
        val time: String? = null,
    ): MessageItem
    data class OtherUserMessageItem(
        val senderId: String? = null,
        val message: String? = null,
        val time: String? = null,
    ):MessageItem
}