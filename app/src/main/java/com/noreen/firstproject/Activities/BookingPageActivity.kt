package com.noreen.firstproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.R
import com.noreen.firstproject.databinding.ActivityBookingPageBinding

class BookingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomName = intent.getStringExtra("ROOM_NAME") ?: "N/A"
        val roomCapacity = intent.getIntExtra("ROOM_CAPACITY", -1)
        val selectedDate = intent.getStringExtra("SELECTED_DATE") ?: "N/A"
        val selectedTime = intent.getStringExtra("SELECTED_TIME") ?: "N/A"
        val status = intent.getStringExtra("STATUS") ?: "Pending"

        // Display Retrieved Data
        binding.tvRoomName.text = "Room: $roomName"
        binding.tvRoomCapacity.text = "Capacity: $roomCapacity"
        binding.tvDate.text = "Date: $selectedDate"

        binding.tvTime.text = "Time: $selectedTime"

        binding.btnProceed.setOnClickListener {
            val reason = binding.etReason.text.toString().trim()
            Toast.makeText(this, "Room booked successfully", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("DATE", selectedDate)
                putExtra("TIME", selectedTime)
                putExtra("ROOM_NAME", roomName)
                putExtra("STATUS", status)
                putExtra("REASON", reason)

            }
            startActivity(intent)
        }
    }
}