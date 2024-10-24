package com.example.catschat.recycler_chats

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catschat.Constants
import com.example.catschat.databinding.ChatsItemBinding
import com.example.catschat.models.Status
import com.example.catschat.recycler_users.UserItem

class ChatViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ChatsItemBinding.bind(view)
    fun bind(chatsItem: ChatsItem, chatListener: ChatListener) = with(binding){
        userName.text = chatsItem.name
        lastMessage.text = chatsItem.lastMessage
        avatar.setImageResource(Constants.catImages[chatsItem.avatarNumber!!])
        time.text = chatsItem.timeLastMessage
        if(chatsItem.status == Status.ONLINE) status.visibility = View.VISIBLE
        itemView.setOnClickListener {
            chatListener.onClick(UserItem(
                chatsItem.id,
                chatsItem.name,
                chatsItem.avatarNumber,
                chatsItem.status
            ))
        }
    }
}