package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.catschat.databinding.FragmentChatsBinding
import com.example.catschat.recycler.ChatAdapter
import com.example.catschat.recycler.ChatListener
import com.example.catschat.recycler.ChatsItem

class ChatsFragment : Fragment(), ChatListener {

    private lateinit var binding: FragmentChatsBinding
    private val adapter: ChatAdapter? by lazy { ChatAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerChats.adapter = adapter
        binding.recyclerChats.layoutManager = LinearLayoutManager(context)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatsFragment()
    }

    override fun onClick(chatsItem: ChatsItem) {
        TODO("Not yet implemented")
    }
}