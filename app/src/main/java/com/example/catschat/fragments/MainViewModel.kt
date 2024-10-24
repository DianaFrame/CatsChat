package com.example.catschat.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catschat.recycler_users.UserItem

class MainViewModel : ViewModel() {

    val selectUser: MutableLiveData<UserItem> by lazy {
        MutableLiveData<UserItem>()
    }
    val signOut: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val addChat: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val openChat: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}