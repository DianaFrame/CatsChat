package com.example.catschat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.catschat.Constants
import com.example.catschat.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private var index = 0
    private val catImages = Constants.catImages

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonRight.setOnClickListener {
                if (index < catImages.lastIndex) index++
                else index = 0
                avatarImageView.setImageResource(catImages[index])
            }
            buttonLeft.setOnClickListener {
                if (index > 0) index--
                else index = catImages.lastIndex
                avatarImageView.setImageResource(catImages[index])
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }
}