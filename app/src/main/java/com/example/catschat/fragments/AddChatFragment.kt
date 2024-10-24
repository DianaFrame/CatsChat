package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catschat.Constants
import com.example.catschat.R
import com.example.catschat.databinding.FragmentAddChatBinding
import com.example.catschat.recycler_users.UserAdapter
import com.example.catschat.recycler_users.UserItem
import com.example.catschat.recycler_users.UserListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddChatFragment : Fragment(), UserListener {

    private lateinit var binding: FragmentAddChatBinding
    private val adapter: UserAdapter by lazy { UserAdapter(this) }
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddChatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val database = Firebase.database
        val usersRef = database.getReference(Constants.USERS_REF)
        val currentUser = auth.currentUser

        usersRef.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<UserItem>()
                for(s in snapshot.children){
                   val user = s.getValue(UserItem::class.java)
                    if(user != null && user.id != currentUser?.uid) users.add(user)
                }
                binding.rcAddNewUser.layoutManager = LinearLayoutManager(context)
                binding.rcAddNewUser.adapter = adapter
                adapter.submitList(users)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddChatFragment()
    }

    override fun onClick(userItem: UserItem) {
        viewModel.selectUser.value = userItem
        val controller = findNavController()
        controller.navigate(R.id.chatFragment)
    }

}