package com.example.catschat.recycler_messages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catschat.databinding.CurrentUserMessageItemBinding

class CurrentUserMessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = CurrentUserMessageItemBinding.bind(view)
    fun bind(currentUserMessageItem: MessageItem.CurrentUserMessageItem, messageListener: MessageListener) = with(binding){
        message.text = currentUserMessageItem.message
        time.text = currentUserMessageItem.time
        itemView.setOnClickListener {
            messageListener.onClick(messageItem = currentUserMessageItem)
        }
    }
}