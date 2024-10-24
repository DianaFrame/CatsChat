package com.example.catschat.recycler_users

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.catschat.Constants
import com.example.catschat.databinding.UsersItemBinding
import com.example.catschat.models.Status

class UserViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = UsersItemBinding.bind(view)
    fun bind(userItem: UserItem, userListener: UserListener) = with(binding){
        username.text = userItem.name
        avatar.setImageResource(Constants.catImages[userItem.avatarNumber ?: 0])
        if(userItem.status == Status.ONLINE) status.visibility = View.VISIBLE
        itemView.setOnClickListener {
            userListener.onClick(userItem = userItem)
        }
    }
}