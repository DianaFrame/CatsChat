package com.example.catschat.fragments

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catschat.Constants
import com.example.catschat.R
import com.example.catschat.databinding.FragmentChatBinding
import com.example.catschat.recycler_chats.ChatsItem
import com.example.catschat.recycler_messages.MessageAdapter
import com.example.catschat.recycler_messages.MessageItem
import com.example.catschat.recycler_messages.MessageListener
import com.example.catschat.recycler_users.UserItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale


class ChatFragment : Fragment(), MessageListener {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChatBinding
    private lateinit var auth: FirebaseAuth
    private var selectUser: UserItem? = null
    private var currentUserId: String? = null
    private val adapter: MessageAdapter by lazy { MessageAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        currentUserId = currentUser?.uid
        val database = Firebase.database
        val usersRef = database.getReference(Constants.USERS_REF)
        val controller = findNavController()
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbarChat)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            binding.toolbarChat.setNavigationOnClickListener {
                controller.navigate(R.id.authUserFragment)
            }
        }
        viewModel.selectUser.observe(viewLifecycleOwner) { userItem ->
            selectUser = userItem
            getMessages(usersRef)
            binding.userAvatar.setImageResource(Constants.catImages[userItem.avatarNumber!!])
            binding.username.text = userItem.name
        }
        binding.rcMessages.adapter = adapter
        binding.rcMessages.layoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
        }
        binding.buttonSend.setOnClickListener {
            sendMessage(usersRef)
            binding.edMessage.text.clear()
        }
    }

    private fun sendMessage(databaseRef: DatabaseReference) = with(binding) {
        val message = edMessage.text.toString().trim()
        val time = getCurrentTime()
        if (message != "") {
            selectUser?.let { selectUser ->
                databaseRef
                    .child(currentUserId!!)
                    .child(Constants.CHATS_REF)
                    .child(selectUser.id!!)
                    .child(Constants.MESSAGES_REF)
                    .child(databaseRef.push().key ?: "any_key")
                    .setValue(
                        MessageItem.CurrentUserMessageItem(
                            senderId = currentUserId,
                            message = message,
                            time = time,
                        )
                    )

                databaseRef
                    .child(selectUser.id)
                    .child(Constants.CHATS_REF)
                    .child(currentUserId!!)
                    .child(Constants.MESSAGES_REF)
                    .child(databaseRef.push().key ?: "any_key")
                    .setValue(
                        MessageItem.OtherUserMessageItem(
                            senderId = currentUserId,
                            message = message,
                            time = time,
                        )
                    )
            }
            saveLastMessage(databaseRef, message, time)
        }
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    private fun getMessages(databaseRef: DatabaseReference) {
        selectUser?.let { selectUser ->
            databaseRef
                .child(currentUserId!!)
                .child(Constants.CHATS_REF)
                .child(selectUser.id!!)
                .child(Constants.MESSAGES_REF)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val messages = ArrayList<MessageItem>()
                        for (s in snapshot.children) {
                            val senderId = s.child("senderId").getValue(String::class.java)
                            if (senderId == currentUserId) {
                                val message =
                                    s.getValue(MessageItem.CurrentUserMessageItem::class.java)
                                if (message != null) messages.add(0, message)
                            } else {
                                val message =
                                    s.getValue(MessageItem.OtherUserMessageItem::class.java)
                                if (message != null) messages.add(0, message)
                            }
                        }
                        adapter.submitList(messages)
                        binding.rcMessages.post {
                            binding.rcMessages.scrollToPosition(0)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun saveLastMessage(dataRef: DatabaseReference, message: String, time: String) {
        selectUser?.let { selectUser ->
            dataRef
                .child(currentUserId!!)
                .child(Constants.CHATS_REF)
                .child(selectUser.id!!)
                .child(Constants.INFORMATION_FOR_CHAT_LIST_REF)
                .setValue(
                    ChatsItem(
                        selectUser.id,
                        selectUser.name,
                        selectUser.status,
                        selectUser.avatarNumber,
                        message,
                        time
                    )
                )

            dataRef.child(currentUserId!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val senderUser = snapshot.getValue(UserItem::class.java)
                        dataRef
                            .child(selectUser.id)
                            .child(Constants.CHATS_REF)
                            .child(currentUserId!!)
                            .child(Constants.INFORMATION_FOR_CHAT_LIST_REF)
                            .setValue(
                                ChatsItem(
                                    senderUser?.id,
                                    senderUser?.name,
                                    senderUser?.status,
                                    senderUser?.avatarNumber,
                                    message,
                                    time
                                )
                            )
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                })
            updateStatus(dataRef)

        }
    }

    private fun updateStatus(dataRef: DatabaseReference) {
        selectUser?.let { selectUser ->
            dataRef.child(selectUser.id!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val receiverUser = snapshot.getValue(UserItem::class.java)
                        dataRef
                            .child(currentUserId!!)
                            .child(Constants.CHATS_REF)
                            .child(selectUser.id)
                            .child(Constants.INFORMATION_FOR_CHAT_LIST_REF)
                            .child("status")
                            .setValue(
                                receiverUser?.status
                            )
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                })
            dataRef.child(currentUserId!!)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val senderUser = snapshot.getValue(UserItem::class.java)
                        dataRef
                            .child(selectUser.id)
                            .child(Constants.CHATS_REF)
                            .child(currentUserId!!)
                            .child(Constants.INFORMATION_FOR_CHAT_LIST_REF)
                            .child("status")
                            .setValue(
                                senderUser?.status
                            )
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }

                })

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChatFragment()
    }

    override fun onClick(messageItem: MessageItem) {
        TODO("Not yet implemented")
    }
}