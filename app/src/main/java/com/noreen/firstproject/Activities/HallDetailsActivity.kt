package com.noreen.firstproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.noreen.firstproject.R
import com.noreen.firstproject.databinding.ActivityHallDetailsBinding

class HallDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHallDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityHallDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve Data from Intent
        val roomName = intent.getStringExtra("ROOM_NAME") ?: "N/A"
        val capacity = intent.getIntExtra("capacity", 0)
        val selectedDate = intent.getStringExtra("DATE") ?: ""
        val selectedTime = intent.getStringExtra("TIME") ?: ""
        binding.tvRoomName.text = "Hall Name: $roomName"
        binding.tvRoomCapacity.text = "Capacity: $capacity"

        binding.btnConfirmBooking.setOnClickListener {
            val intent = Intent(this, BookingPageActivity::class.java).apply {
                putExtra("ROOM_NAME", roomName)
                putExtra("ROOM_CAPACITY", capacity)
                putExtra("SELECTED_DATE", selectedDate)
                putExtra("SELECTED_TIME", selectedTime)
                putExtra("STATUS", "Pending")
            }
            startActivity(intent)
        }
    }
}