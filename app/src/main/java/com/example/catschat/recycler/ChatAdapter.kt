package com.example.catschat.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.catschat.R

class ChatAdapter(private val chatListener: ChatListener): ListAdapter<ChatsItem, ChatViewHolder>(Comparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chats_item, parent, false)
        return ChatViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        return holder.bind(getItem(position), chatListener = chatListener)
    }
}