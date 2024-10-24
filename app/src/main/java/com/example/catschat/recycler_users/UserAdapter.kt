package com.example.catschat.recycler_users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.catschat.R

class UserAdapter(private val userListener: UserListener): ListAdapter<UserItem, UserViewHolder>(UserComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.users_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        return holder.bind(getItem(position), userListener = userListener)
    }
}