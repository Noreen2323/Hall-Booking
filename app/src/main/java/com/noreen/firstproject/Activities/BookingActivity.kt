package com.noreen.firstproject.Activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.noreen.firstproject.R
import java.util.*

class BookingActivity : AppCompatActivity() {
    private lateinit var tvSelectedDateTime: TextView
    private var selectedDate = ""
    private var selectedTime = ""
    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        val btnPickDate = findViewById<Button>(R.id.btnPickDate)
        val btnPickTime = findViewById<Button>(R.id.btnPickTime)
        val btnBook = findViewById<Button>(R.id.btnBook)
        tvSelectedDateTime = findViewById(R.id.tvSelectedDateTime)

        btnPickDate.setOnClickListener {
            showDatePicker()
        }
        btnPickTime.setOnClickListener {
            showTimePicker()
        }
        btnBook.setOnClickListener {
            bookAppointment()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance() // Current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, pickedYear, pickedMonth, pickedDay ->
            selectedYear = pickedYear
            selectedMonth = pickedMonth
            selectedDay = pickedDay
            selectedDate = "$pickedDay/${pickedMonth + 1}/$pickedYear"
            updateDateTimeText()
        }, year, month, day)

        // Disable past dates
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        var minHour = 0
        var minMinute = 0

        // If the selected date is today, restrict time selection to future times
        if (selectedYear == calendar.get(Calendar.YEAR) &&
            selectedMonth == calendar.get(Calendar.MONTH) &&
            selectedDay == calendar.get(Calendar.DAY_OF_MONTH)) {
            minHour = currentHour
            minMinute = currentMinute
        }

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            if (selectedYear == calendar.get(Calendar.YEAR) &&
                selectedMonth == calendar.get(Calendar.MONTH) &&
                selectedDay == calendar.get(Calendar.DAY_OF_MONTH) &&
                (selectedHour < minHour || (selectedHour == minHour && selectedMinute < minMinute))) {
                Toast.makeText(this, "Cannot select past time!", Toast.LENGTH_SHORT).show()
                return@TimePickerDialog
            }

            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            updateDateTimeText()
        }, currentHour, currentMinute, true)

        timePickerDialog.show()
    }

    private fun updateDateTimeText() {
        tvSelectedDateTime.text = "Selected Date & Time: $selectedDate $selectedTime"
    }

    private fun bookAppointment() {
        if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Please select a date and time", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, AvailableHalls::class.java).apply {
            putExtra("SELECTED_DATE", selectedDate)
            putExtra("SELECTED_TIME", selectedTime)
        }
        startActivity(intent)
    }
}
