package com.example.catschat.recycler_messages

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catschat.databinding.OtherUserMessageItemBinding

class OtherUserMessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = OtherUserMessageItemBinding.bind(view)
    fun bind(otherUserMessageItem: MessageItem.OtherUserMessageItem, messageListener: MessageListener) = with(binding){
        message.text = otherUserMessageItem.message
        time.text = otherUserMessageItem.time
        itemView.setOnClickListener {
            messageListener.onClick(messageItem = otherUserMessageItem)
        }
    }
}