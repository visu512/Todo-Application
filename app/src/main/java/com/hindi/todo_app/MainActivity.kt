package com.hindi.todo_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hindi.todo_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                // User is already logged in → go to HomeActivity
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // User not logged in → go to LoginActivity
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish() // Finish MainActivity so user can't go back here
        }
    }
}
