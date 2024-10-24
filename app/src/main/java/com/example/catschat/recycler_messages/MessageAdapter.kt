package com.example.catschat.recycler_messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catschat.Constants
import com.example.catschat.R

class MessageAdapter(private val messageListener: MessageListener) :
    ListAdapter<MessageItem, RecyclerView.ViewHolder>(MessageComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Constants.CURRENT_USER_TYPE -> {
                CurrentUserMessageViewHolder(
                    inflater.inflate(R.layout.current_user_message_item, parent, false)
                )
            }

            Constants.OTHER_USER_TYPE -> {
                OtherUserMessageViewHolder(
                    inflater.inflate(R.layout.other_user_message_item, parent, false)
                )
            }

            else -> throw RuntimeException("Unknown type: $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrentUserMessageViewHolder -> holder.bind(
                getItem(position) as MessageItem.CurrentUserMessageItem,
                messageListener
            )

            is OtherUserMessageViewHolder -> holder.bind(
                getItem(position) as MessageItem.OtherUserMessageItem,
                messageListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MessageItem.CurrentUserMessageItem -> Constants.CURRENT_USER_TYPE
            is MessageItem.OtherUserMessageItem -> Constants.OTHER_USER_TYPE
        }
    }
}