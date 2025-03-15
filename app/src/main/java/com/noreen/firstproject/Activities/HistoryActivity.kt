package com.noreen.firstproject.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.DataModel.Bookinghistory
import com.noreen.firstproject.R
import com.noreen.firstproject.adapters.BookingHistoryAdapter
import com.noreen.firstproject.databinding.ActivityHistory2Binding

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: BookingHistoryAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history2)

        recyclerView = findViewById(R.id.recyclerViewHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        historyAdapter = BookingHistoryAdapter(emptyList())
        recyclerView.adapter = historyAdapter

        fetchBookingHistory()
    }
    private fun fetchBookingHistory() {
        val sharedPreferences = getSharedPreferences("Projectuni", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", null)

        if (userEmail == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }
        db.collection("Bookings")
            .whereEqualTo("userEmail", userEmail).get()
            .addOnSuccessListener { documents ->
                val bookingList = mutableListOf<Bookinghistory>()
                for (document in documents) {
                    val date = document.getString("date") ?: ""
                    val time = document.getString("time") ?: ""
                    val roomName = document.getString("roomName") ?: ""
                    val status = document.getString("status") ?: "Pending"

                    bookingList.add(Bookinghistory(date, time, roomName, status))
                }
                historyAdapter.updateList(bookingList)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}

