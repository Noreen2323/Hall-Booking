package com.noreen.firstproject.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.noreen.firstproject.R
import com.noreen.firstproject.databinding.FirstscreenBinding

class firstscreen : AppCompatActivity() {
    private lateinit var binding: FirstscreenBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize LottieAnimationView
        lottieAnimationView = findViewById(R.id.lottieAnimationView)
        lottieAnimationView.setAnimation("giff.lottie") //Yeh lottieAnimationView me Lottie JSON animation file load kar raha hai.
        lottieAnimationView.repeatCount = LottieDrawable.INFINITE

        binding.getstartedbtn.setOnClickListener {
            lottieAnimationView.playAnimation()

            val intent = Intent(this@firstscreen, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
