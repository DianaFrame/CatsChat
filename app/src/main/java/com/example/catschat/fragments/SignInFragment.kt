package com.example.catschat.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catschat.Constants
import com.example.catschat.R
import com.example.catschat.databinding.FragmentSignInBinding
import com.example.catschat.models.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        auth = Firebase.auth
        var currentUser = auth.currentUser
        if(currentUser != null){
            controller.navigate(R.id.authUserFragment)
        }
        binding.registrationButton.setOnClickListener {
            controller.navigate(R.id.registrationFragment)
        }
        binding.signInButton.setOnClickListener {
            if(checkForBlankFields()) {
                val email = binding.edEmail.text.toString()
                val password = binding.edPassword.text.toString()
                val database = Firebase.database
                val usersRef = database.getReference(Constants.USERS_REF)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            currentUser = auth.currentUser
                            usersRef.child(currentUser!!.uid).child("status").setValue(Status.ONLINE)
                            controller.navigate(R.id.authUserFragment)
                        } else {
                            Toast.makeText(
                                context,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            binding.apply {
                                edEmail.text.clear()
                                edPassword.text.clear()
                            }
                        }

                    }
            }
        }
        binding.forgotPasswordTextView.setOnClickListener {
            controller.navigate(R.id.forgotPasswordFragment)
        }
    }
    private fun checkForBlankFields(): Boolean = with(binding) {
        var flag = 0
        if (edEmail.text.toString() == "") {
            edEmail.setHintTextColor(Color.RED)
            flag = 1
        } else edEmail.setHintTextColor(null)
        if (edPassword.text.toString() == "") {
            edPassword.setHintTextColor(Color.RED)
            flag = 1
        } else edPassword.setHintTextColor(null)
        if(flag == 1) return@with false
        return@with true
    }


    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}