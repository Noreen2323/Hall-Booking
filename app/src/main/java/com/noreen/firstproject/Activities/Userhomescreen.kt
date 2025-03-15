package com.noreen.firstproject.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noreen.firstproject.databinding.UserhomescreenBinding

class Userhomescreen : AppCompatActivity() {
    private lateinit var binding: UserhomescreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserhomescreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences: SharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("username", "User") // Default: "User"


        binding.welcom.text = "Welcome, $userName!"

        binding.apply {
            btnHistory.setOnClickListener {
                startActivity(Intent(this@Userhomescreen, HistoryActivity::class.java))
            }

            btnBooking.setOnClickListener {
                startActivity(Intent(this@Userhomescreen, BookingActivity::class.java))
            }

            btnLogout.setOnClickListener {

                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()

                startActivity(Intent(this@Userhomescreen, Loginscreen::class.java))
                finish()
            }
        }
    }
}
