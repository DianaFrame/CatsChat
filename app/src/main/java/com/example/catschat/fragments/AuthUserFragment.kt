package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catschat.R
import com.example.catschat.databinding.FragmentAuthUserBinding

class AuthUserFragment : Fragment(){

    private lateinit var binding: FragmentAuthUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        binding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.icon_chats->{controller.navigate(R.id.chatsFragment)}
                R.id.icon_cat->{controller.navigate(R.id.gameFragment)}
                R.id.icon_settings->{controller.navigate(R.id.settingsFragment)}
            }
            true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AuthUserFragment()
    }

}