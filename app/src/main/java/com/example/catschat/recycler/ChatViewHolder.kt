package com.example.catschat.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catschat.databinding.ChatsItemBinding

class ChatViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ChatsItemBinding.bind(view)
    fun bind(chatsItem: ChatsItem, chatListener: ChatListener) = with(binding){
        userName.text = chatsItem.name
        lastMessage.text = chatsItem.lastMessage
        itemView.setOnClickListener {
            chatListener.onClick(chatsItem)
        }
    }
}