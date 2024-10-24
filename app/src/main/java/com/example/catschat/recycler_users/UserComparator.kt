package com.example.catschat.recycler_users

import androidx.recyclerview.widget.DiffUtil

class UserComparator : DiffUtil.ItemCallback<UserItem>(){
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
        return oldItem == newItem
    }

}