package com.noreen.firstproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.noreen.firstproject.databinding.ActivityAdminLoginBinding

class AdminLogin : AppCompatActivity() {

    private lateinit var binding:ActivityAdminLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hardcoded admin credentials
        val adminEmail = "admin23"
        val adminPassword = "admin"

        // On Login button click
        binding.loginbtn.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            // Check if the entered credentials match the hardcoded ones
            if (email == adminEmail && password == adminPassword) {
                // If credentials are correct, move to the HomeScreen
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, AdminHomeActivity::class.java)
                startActivity(intent)
                finish() // Finish the AdminMainscreen so the user can't navigate back to it
            } else {
                // If credentials are incorrect
                Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show()
            }
            }
        }
}
