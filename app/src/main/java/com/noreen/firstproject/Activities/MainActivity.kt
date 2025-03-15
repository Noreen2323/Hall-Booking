package com.noreen.firstproject.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noreen.firstproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
binding.userbtn.setOnClickListener()
{
    startActivity(Intent(this@MainActivity, UserSignup::class.java))

}
        binding.adminbtn.setOnClickListener(){
            startActivity(Intent(this@MainActivity, AdminLogin::class.java))
        }
    }
    }
