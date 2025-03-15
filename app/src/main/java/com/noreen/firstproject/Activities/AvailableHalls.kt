package com.noreen.firstproject.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.DataModel.Hall
import com.noreen.firstproject.HallAdapter
import com.noreen.firstproject.R

class AvailableHalls : AppCompatActivity() {

    private lateinit var recyclerViewRooms: RecyclerView
    private lateinit var roomAdapter: HallAdapter
    private val db = FirebaseFirestore.getInstance()
    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_halls)
        selectedDate = intent.getStringExtra("SELECTED_DATE") ?: ""
        selectedTime = intent.getStringExtra("SELECTED_TIME") ?: ""

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms)
        recyclerViewRooms.layoutManager = LinearLayoutManager(this)
        roomAdapter = HallAdapter(emptyList()) { selectedRoom -> bookRoom(
                selectedRoom, selectedDate, selectedTime,
                capacity = selectedRoom.capacity
            )
        }
        recyclerViewRooms.adapter = roomAdapter

        fetchAvailableRooms()
    }
    private fun fetchAvailableRooms() {
        db.collection("Rooms").whereEqualTo("availability", true).get()
            .addOnSuccessListener { documents ->
                val roomList = mutableListOf<Hall>()
                for (document in documents) {
                    val id = document.id
                    val name = document.getString("name") ?: ""
                    val capacity = document.getLong("capacity")?.toInt() ?: 0
                    val availability = document.getBoolean("availability") ?: true

                    roomList.add(Hall(id, name, capacity, availability))
                    //Creates a Hall object with the retrieved data.
                    //Adds the room to roomList.
                }
                roomAdapter.updateList(roomList)
                //updates the RecyclerView adapter with the new list of available rooms.
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun bookRoom(room: Hall, selectedDate: String, selectedTime: String , capacity:Int) {
        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(
                this@AvailableHalls,
                "Please select a date and time",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val sharedPreferences = getSharedPreferences("Projectuni", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("email", "UnknownEmail")

        val bookingData = hashMapOf(
            "userEmail" to userEmail,
            "roomName" to room.hallname,
            "date" to selectedDate,
            "time" to selectedTime,
            "status" to "Pending"
        )
        db.collection("Bookings").add(bookingData)
            .addOnSuccessListener {

                navigateToHistory(selectedDate, selectedTime, room.hallname , capacity)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun navigateToHistory(date: String, time: String, roomName: String, capacity:Int) {
        val intent = Intent(this, HallDetailsActivity::class.java).apply {
            putExtra("DATE", date)
            putExtra("TIME", time)
            putExtra("ROOM_NAME", roomName)
            putExtra("STATUS", "Pending")
            putExtra("capacity", capacity)
        }
        startActivity(intent)
    }
}

