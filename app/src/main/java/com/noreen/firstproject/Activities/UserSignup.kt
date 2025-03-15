package com.noreen.firstproject.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.DataModel.Usermodel
import com.noreen.firstproject.databinding.ActivityUserSignupBinding

class UserSignup : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignupBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupbtn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            // ✅ Check if fields are empty
            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Check if email is in valid format
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.email.error = "Invalid Email Address"
                return@setOnClickListener
            }

            // ✅ Check if email is already registered
            db.collection("User").whereEqualTo("email", email).get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        Toast.makeText(this, "This email is already registered", Toast.LENGTH_SHORT).show()
                    } else {
                        val newUser = Usermodel(name = name, email = email, password = password)

                        // ✅ Save user data to Firestore
                        db.collection("User").add(newUser)
                            .addOnSuccessListener {
                                // ✅ Store username in SharedPreferences
                                val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("username", name)
                                editor.apply()

                                Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show()

                                // ✅ Navigate to HomeScreen instead of LoginScreen
                                startActivity(Intent(this,Userhomescreen::class.java))
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Signup failed: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error checking email: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // ✅ Navigate to Login Screen
        binding.loginbtn.setOnClickListener {
            startActivity(Intent(this, Loginscreen::class.java))
        }
    }
}
