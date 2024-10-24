package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catschat.Constants
import com.example.catschat.R
import com.example.catschat.databinding.FragmentChatListBinding
import com.example.catschat.recycler_chats.ChatAdapter
import com.example.catschat.recycler_chats.ChatListener
import com.example.catschat.recycler_chats.ChatsItem
import com.example.catschat.recycler_users.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatListFragment : Fragment(), ChatListener, MenuProvider {

    private lateinit var binding: FragmentChatListBinding
    private val adapter: ChatAdapter by lazy { ChatAdapter(this) }
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by activityViewModels()
    private var  currentUserId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Firebase.database
        val userRef = database.getReference(Constants.USERS_REF)
        binding.recyclerChats.adapter = adapter
        binding.recyclerChats.layoutManager = LinearLayoutManager(context)
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        requireActivity().addMenuProvider(this, viewLifecycleOwner)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUserId = currentUser?.uid
        getChatList(userRef)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatListFragment()
    }

    override fun onClick(userItem: UserItem) {
        viewModel.selectUser.value = userItem
        viewModel.openChat.value = true
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.sign_out->{
                auth.signOut()
                viewModel.signOut.value = true
                Toast.makeText(context, "Sign out", Toast.LENGTH_SHORT).show()
            }
            R.id.add_chat->{
                viewModel.addChat.value = true
                Toast.makeText(context, "New chat", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
    private fun getChatList(dataRef: DatabaseReference){
        dataRef
            .child(currentUserId!!)
            .child(Constants.CHATS_REF)
            .addValueEventListener( object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chats = ArrayList<ChatsItem>()
                    for(s in snapshot.children){
                        val chat = s
                            .child(Constants.INFORMATION_FOR_CHAT_LIST_REF)
                            .getValue(ChatsItem::class.java)
                        if(chat != null) chats.add(chat)
                    }
                    adapter.submitList(chats)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}