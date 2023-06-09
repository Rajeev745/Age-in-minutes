package com.example.ageinminutes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var datePickerBtn: Button
    private lateinit var selectedDateTextView: TextView
    private lateinit var resultDateTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datePickerBtn = findViewById(R.id.dob_selector_button)
        selectedDateTextView = findViewById(R.id.selected_dob)
        resultDateTextView = findViewById(R.id.result_age_text)

        datePickerBtn.setOnClickListener {
            selectDate()
        }
    }

    private fun selectDate() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        // For getting the date dialog box
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // For getting the value of date
                val selectedDateString = "$dayOfMonth/$month/$year"
                selectedDateTextView.text = selectedDateString
                val dateFormat = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
                val date = dateFormat.parse(selectedDateString)

                date?.let {
                    val selectedDateInMinutes = date.time / 60000
                    val currentTimeInMinutes =
                        dateFormat.parse(dateFormat.format(System.currentTimeMillis()))!!.time / 60000
                    val ageInMinutes = currentTimeInMinutes - selectedDateInMinutes
                    resultDateTextView.text = ageInMinutes.toString()
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialog.show()
    }
}