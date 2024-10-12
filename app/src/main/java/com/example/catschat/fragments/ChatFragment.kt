package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catschat.databinding.FragmentChatBinding
import com.example.catschat.recycler.ChatAdapter
import com.example.catschat.recycler.ChatListener
import com.example.catschat.recycler.ChatsItem

class ChatFragment : Fragment(), ChatListener {

    private lateinit var binding: FragmentChatBinding
    private val adapter: ChatAdapter by lazy { ChatAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override fun onClick(chatsItem: ChatsItem) {
        TODO("Not yet implemented")
    }
}