package com.noreen.firstproject.adapters

import BookingAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noreen.firstproject.DataModel.Bookinghistory
import com.noreen.firstproject.R


class BookingHistoryAdapter(private var historyList: List<Bookinghistory>) :
    RecyclerView.Adapter<BookingHistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvRoomName: TextView = itemView.findViewById(R.id.tvRoomName)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.tvDate.text = "Date: ${history.date}"
        holder.tvTime.text = "Time: ${history.time}"
        holder.tvRoomName.text = "Room: ${history.roomName}"
        holder.tvStatus.text = "Status: ${history.status}"

    }

    override fun getItemCount(): Int = historyList.size

    fun updateList(newList: List<Bookinghistory>) {
        historyList = newList
        notifyDataSetChanged()
    }
}