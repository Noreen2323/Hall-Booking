package com.noreen.firstproject.Activities

import BookingAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.DataModel.ModelBooking
import com.noreen.firstproject.R

class AdminHomeActivity: AppCompatActivity() {
    private lateinit var recyclerViewBookings: RecyclerView
    private lateinit var btnAddRoom: MaterialButton
    private lateinit var bookingAdapter: BookingAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adminhomeactivity)

        recyclerViewBookings = findViewById(R.id.recyclerViewRooms)
        btnAddRoom = findViewById(R.id.btnAddRoom)

        recyclerViewBookings.layoutManager = LinearLayoutManager(this)

        bookingAdapter = BookingAdapter(emptyList())
        recyclerViewBookings.adapter = bookingAdapter

        btnAddRoom.setOnClickListener {
            showAddRoomDialog()
        }
        fetchAllBookings()
    }

    private fun showAddRoomDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_room, null)
        val etRoomName = dialogView.findViewById<EditText>(R.id.etRoomName)
        val etCapacity = dialogView.findViewById<EditText>(R.id.etCapacity)
        val btnAddRoom = dialogView.findViewById<MaterialButton>(R.id.btnAddRoom)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnAddRoom.setOnClickListener {
            val roomName = etRoomName.text.toString().trim()
            val capacity = etCapacity.text.toString().trim()

            if (roomName.isEmpty() || capacity.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val roomData = hashMapOf(
                "name" to roomName,
                "capacity" to capacity.toInt(),
                "availability" to true
            )

            db.collection("Rooms").add(roomData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Room added successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        dialog.show()
    }

    private fun fetchAllBookings() {
        db.collection("Bookings")
            .get()
            .addOnSuccessListener { documents ->
                val bookingList = mutableListOf<ModelBooking>()
                for (document in documents) {
                    val id = document.id
                    val email = document.getString("userEmail") ?: "Unknown"
                    val roomName = document.getString("roomName") ?: "No Room"
                    val date = document.getString("date") ?: "No Date"
                    val time = document.getString("time") ?: "No Time"
                    val status = document.getString("status") ?: "Pending"

                    bookingList.add(ModelBooking(id,email, roomName, date,time))
                }
                bookingAdapter.updateList(bookingList)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
