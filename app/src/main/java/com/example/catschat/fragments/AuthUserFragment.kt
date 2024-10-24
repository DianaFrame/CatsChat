package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.catschat.Constants
import com.example.catschat.R
import com.example.catschat.databinding.FragmentAuthUserBinding
import com.example.catschat.models.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AuthUserFragment : Fragment() {

    private lateinit var binding: FragmentAuthUserBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        val database = Firebase.database
        val usersRef = database.getReference(Constants.USERS_REF)
        viewModel.signOut.observe(viewLifecycleOwner){
            if(it == true) {
                val controller = findNavController()
                controller.navigate(R.id.signInFragment)
                viewModel.signOut.value = false
                usersRef.child(currentUser!!.uid).child("status").setValue(Status.OFFLINE)
            }
        }
        viewModel.addChat.observe(viewLifecycleOwner){
            if(it == true){
                val controller = findNavController()
                controller.navigate(R.id.addChatFragment)
                viewModel.addChat.value = false

            }
        }
        viewModel.openChat.observe(viewLifecycleOwner){
            if(it == true){
                val controller = findNavController()
                controller.navigate(R.id.chatFragment)
                viewModel.openChat.value = false
            }
        }
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerViewAuthUser) as NavHostFragment
        val controller = navHostFragment.findNavController()
        binding.bottomMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.icon_chats -> {
                    controller.navigate(R.id.chatsFragment)
                }

                R.id.icon_cat -> {
                    controller.navigate(R.id.gameFragment)
                }

                R.id.icon_settings -> {
                    controller.navigate(R.id.settingsFragment)
                }
            }
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AuthUserFragment()
    }



}