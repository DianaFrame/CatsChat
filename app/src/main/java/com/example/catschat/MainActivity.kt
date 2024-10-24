package com.example.catschat

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.catschat.models.Status
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onResume() {
        super.onResume()
        val currentUser = auth.currentUser
        if(currentUser!=null) {
            val database = Firebase.database
            val usersRef = database.getReference(Constants.USERS_REF)
            usersRef.child(currentUser.uid).child("status").setValue(Status.ONLINE)
        }
    }

    override fun onPause() {
        super.onPause()
        val currentUser = auth.currentUser
        if(currentUser!=null) {
            val database = Firebase.database
            val usersRef = database.getReference(Constants.USERS_REF)
            usersRef.child(currentUser.uid).child("status").setValue(Status.OFFLINE)
        }
    }
}