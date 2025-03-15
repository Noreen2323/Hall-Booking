package com.noreen.firstproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.noreen.firstproject.Activities.HallDetailsActivity
import com.noreen.firstproject.DataModel.Hall

class HallAdapter(
         private var roomList: List<Hall>,
         //Suppose there are multiple halls in the RecyclerView.Each row has a "Book" button.
    //When the user clicks a specific hall's button, we need to send that specific hall's data to the function.
    //room contains the hall's name, capacity, and availability, so we pass it.
         private val onRoomClick: (Hall) -> Unit
         ) : RecyclerView.Adapter<HallAdapter.RoomViewHolder>() {

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        val tvCapacity: TextView = itemView.findViewById(R.id.tvCapacity)
        val btnBook: MaterialButton = itemView.findViewById(R.id.btnBook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.tvRoomName.text = room.hallname
        holder.tvCapacity.text = "Capacity: ${room.capacity}"

        holder.btnBook.setOnClickListener {
            onRoomClick(room)
        }
    }

    override fun getItemCount(): Int = roomList.size


    //This function updates the RecyclerView's data list with a new list and refreshes the UI.
    fun updateList(newList: List<Hall>) {
        roomList = newList //// Update the adapter's room list with the new list
        notifyDataSetChanged()// // Refresh the RecyclerView
    }
}

//Adapter Class (RecyclerView Adapter)
//Ye RecyclerView aur Model Class ko connect karta hai.
//Ye data ko layout mein bind karta hai.
//ViewHolder ke through har item ka layout set hota hai.