package com.noreen.firstproject.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.databinding.ActivityLoginscreenBinding

class Loginscreen : AppCompatActivity() {

    private lateinit var binding: ActivityLoginscreenBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var btnForget: TextView
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = getSharedPreferences("Projectuni", MODE_PRIVATE)
        var editor = sharedPref.edit()
        binding.apply {
            loginbtn.setOnClickListener() {
                var email = binding.email.text.toString()
                var password =binding.password.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@Loginscreen, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                db.collection("User").whereEqualTo("email", email).whereEqualTo("password", password).get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            editor.putString("email", email)
                            editor.apply()

                            Toast.makeText(this@Loginscreen, "Login successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@Loginscreen,Userhomescreen::class.java).apply {
                                putExtra("USER_EMAIL", email)
                                putExtra("USER_PASSWORD", password)
                            }
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Loginscreen, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@Loginscreen, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }}}
