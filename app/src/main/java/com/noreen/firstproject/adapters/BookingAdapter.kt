
/*import com.noreen.firstproject.R
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.DataModel.ModelBooking


class BookingAdapter(
    private var bookings: List<ModelBooking>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val btnDetails: Button = view.findViewById(R.id.btnDetails)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_booking_admin, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.tvEmail.text = booking.email
        holder.tvStatus.text = booking.status

        holder.btnDetails.setOnClickListener {
            showBookingDetailsDialog(holder.itemView.context, booking)
        }
    }

    override fun getItemCount(): Int = bookings.size

    fun updateList(newList: List<ModelBooking>) {
        bookings = newList
        notifyDataSetChanged()
    }

    private fun showBookingDetailsDialog(context: Context, booking: ModelBooking) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_booking_details, null)

        val tvEmail = dialogView.findViewById<TextView>(R.id.tvEmail)
        val tvRoom = dialogView.findViewById<TextView>(R.id.tvRoom)
        val tvDate = dialogView.findViewById<TextView>(R.id.tvDate)
        val tvTime = dialogView.findViewById<TextView>(R.id.tvTime)
        val btnReject = dialogView.findViewById<Button>(R.id.btnReject)
        val btnApprove = dialogView.findViewById<Button>(R.id.btnApprove)

        tvEmail.text = "Email: ${booking.email}"
        tvRoom.text = "Room: ${booking.roomName}"
        tvDate.text = "Date: ${booking.date}"
        tvTime.text = "Time: ${booking.time}"

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Booking Details")
            .setPositiveButton("Close") { dialogInterface, _ -> dialogInterface.dismiss() }
            .create()

        btnReject.setOnClickListener {
            updateBookingStatus(context, booking, "Rejected")
            dialog.dismiss()
        }

        btnApprove.setOnClickListener {
            updateBookingStatus(context, booking, "Approved")
            dialog.dismiss()
        }

        dialog.show()
    }

}private fun updateBookingStatus(context: Context, booking: ModelBooking, status: String) {
    val db = FirebaseFirestore.getInstance()

    val bookingId = booking.id

    db.collection("Bookings").document(bookingId)
        .update("status", status)
        .addOnSuccessListener {
            Toast.makeText(context, "Booking status updated to $status", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error updating status: ${e.message}", Toast.LENGTH_SHORT).show()
        }

}
*/
import com.noreen.firstproject.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.noreen.firstproject.DataModel.ModelBooking

class BookingAdapter(
    private var bookings: List<ModelBooking>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmail: TextView = view.findViewById(R.id.tvEmail)
        val btnDetails: Button = view.findViewById(R.id.btnDetails)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_booking_admin, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.tvEmail.text = booking.email
        holder.tvStatus.text = booking.status

        // Set text color based on status
        holder.tvStatus.setTextColor(if (booking.status == "Approved") Color.GREEN else Color.RED)

        holder.btnDetails.setOnClickListener {
            showBookingDetailsDialog(holder.itemView.context, booking, holder)
        }
    }

    override fun getItemCount(): Int = bookings.size

    fun updateList(newList: List<ModelBooking>) {
        bookings = newList
        notifyDataSetChanged()
    }

    private fun showBookingDetailsDialog(
        context: Context,
        booking: ModelBooking,
        holder: BookingViewHolder
    ) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_booking_details, null)

        val tvEmail = dialogView.findViewById<TextView>(R.id.tvEmail)
        val tvRoom = dialogView.findViewById<TextView>(R.id.tvRoom)
        val tvDate = dialogView.findViewById<TextView>(R.id.tvDate)
        val tvTime = dialogView.findViewById<TextView>(R.id.tvTime)
        val btnReject = dialogView.findViewById<Button>(R.id.btnReject)
        val btnApprove = dialogView.findViewById<Button>(R.id.btnApprove)

        tvEmail.text = "Email: ${booking.email}"
        tvRoom.text = "Room: ${booking.roomName}"
        tvDate.text = "Date: ${booking.date}"
        tvTime.text = "Time: ${booking.time}"

        val dialog = AlertDialog.Builder(context)
           .setView(dialogView)
            .setCancelable(true)
            .create()

        btnReject.setOnClickListener {
            updateBookingStatus(context, booking, "Rejected", holder)
            dialog.dismiss()
        }

        btnApprove.setOnClickListener {
            updateBookingStatus(context, booking, "Approved", holder)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateBookingStatus(
        context: Context,
        booking: ModelBooking,
        status: String,
        holder: BookingViewHolder
    ) {
        val db = FirebaseFirestore.getInstance()
        val bookingId = booking.id

        db.collection("Bookings").document(bookingId).update("status", status)
            .addOnSuccessListener {
                Toast.makeText(context, "Booking status updated to $status", Toast.LENGTH_SHORT)
                    .show()

                // Update status in UI
                booking.status = status
                holder.tvStatus.text = status
                holder.tvStatus.setTextColor(if (status == "Approved") Color.GREEN else Color.RED)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error updating status: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}
