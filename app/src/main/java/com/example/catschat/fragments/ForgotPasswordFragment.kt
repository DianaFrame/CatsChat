package com.example.catschat.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catschat.R
import com.example.catschat.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        auth = Firebase.auth
        binding.buttonForgotPassword.setOnClickListener {
            if (checkForBlankFields()) {
                auth.sendPasswordResetEmail(binding.edForgotPassword.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                context,
                                "Password reset email send successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            controller.navigate(R.id.signInFragment)
                        } else {
                            Toast.makeText(
                                context,
                                "Password reset email send failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                        binding.edForgotPassword.text.clear()
                    }
            }
        }
    }

    private fun checkForBlankFields(): Boolean = with(binding) {
        var flag = 0
        if (edForgotPassword.text.toString() == "") {
            edForgotPassword.setHintTextColor(Color.RED)
            flag = 1
        } else edForgotPassword.setHintTextColor(null)
        if (flag == 1) return@with false
        return@with true
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgotPasswordFragment()
    }
}