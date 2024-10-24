package com.example.catschat.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.catschat.Constants
import com.example.catschat.R
import com.example.catschat.databinding.FragmentRegistrationBinding
import com.example.catschat.models.Status
import com.example.catschat.models.UserProfileInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private var index = 0
    private val catImages = Constants.catImages
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val controller = findNavController()
        val database = Firebase.database
        val usersRef = database.getReference(Constants.USERS_REF)
        auth = Firebase.auth
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
            registrationButton.setOnClickListener {
                if (checkForBlankFields()) {
                    val email = edEmail.text.toString()
                    val password = edPassword.text.toString()
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val currentUser = auth.currentUser
                                usersRef.child(currentUser!!.uid).setValue(
                                    UserProfileInformation(
                                        currentUser.uid,
                                        index,
                                        edUserName.text.toString(),
                                        edPhone.text.toString(),
                                        edUserBirthday.text.toString(),
                                        edEmail.text.toString(),
                                        edPassword.text.toString(),
                                        Status.ONLINE,
                                    )
                                )
                                controller.navigate(R.id.authUserFragment)
                            } else {
                                Toast.makeText(
                                    context,
                                    "${task.exception}",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                        }
                }
            }
        }
    }


    private fun checkForBlankFields(): Boolean = with(binding) {
        var flag = 0
        if (edUserName.text.toString() == "") {
            edUserName.setHintTextColor(Color.RED)
            flag = 1
        } else edUserName.setHintTextColor(null)
        if (edEmail.text.toString() == "") {
            edEmail.setHintTextColor(Color.RED)
            flag = 1
        } else edEmail.setHintTextColor(null)
        if (edPhone.text.toString() == "") {
            edPhone.setHintTextColor(Color.RED)
            flag = 1
        } else edPhone.setHintTextColor(null)
        if (edUserBirthday.text.toString() == "") {
            edUserBirthday.setHintTextColor(Color.RED)
            flag = 1
        } else edUserBirthday.setHintTextColor(null)
        if (edPassword.text.toString() == "") {
            edPassword.setHintTextColor(Color.RED)
            flag = 1
        } else edPassword.setHintTextColor(null)
        if (flag == 1) return@with false
        return@with true
    }


    companion object {
        @JvmStatic
        fun newInstance() = RegistrationFragment()
    }
}