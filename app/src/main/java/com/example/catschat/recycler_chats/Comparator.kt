package com.example.catschat.recycler_chats

import androidx.recyclerview.widget.DiffUtil

class Comparator: DiffUtil.ItemCallback<ChatsItem>() {
    override fun areItemsTheSame(oldItem: ChatsItem, newItem: ChatsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatsItem, newItem: ChatsItem): Boolean {
        return oldItem == newItem
    }
}