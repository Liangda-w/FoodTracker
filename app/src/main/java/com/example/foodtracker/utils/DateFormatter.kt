package com.example.foodtracker.utils

import android.graphics.Color
import android.graphics.Typeface
import android.widget.DatePicker
import android.widget.TextView
import com.example.foodtracker.R
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

// For Logging
private val TAG = "AddFragment"

fun formatExpirationDate(date: LocalDate, textView: TextView) {
    val today = LocalDate.now(ZoneId.systemDefault())

//    Log.d(TAG, today.toString())
//    Log.d(TAG, date.toString())

    val period = Period.between(today, date)
//    Log.d(TAG, period.isNegative.toString())
//    Log.d(TAG, period.years.toString())
//    Log.d(TAG, period.months.toString())
//    Log.d(TAG, period.days.toString())

    if (period.isNegative) {
        textView.text = date.toString()
        textView.setTextColor(Color.RED)
    } else if (period.years > 0 || period.months > 0 || period.days > 7) {
        textView.text = date.toString()
    } else if (period.days == 0) {
        textView.text = textView.context.getString(R.string.today)
        textView.setTextColor(Color.parseColor("#f79502"))
        textView.setTypeface(null, Typeface.BOLD);
    } else if (period.days == 1) {
        textView.text = textView.context.getString(R.string.tomorrow)
        textView.setTextColor(Color.parseColor("#f7ca02"))
        textView.setTypeface(null, Typeface.BOLD);
    } else {
        textView.text =
            textView.context.getString(R.string.expiration_in_x_days, period.days.toString())
        textView.setTextColor(Color.parseColor("#52ad11"))
        textView.setTypeface(null, Typeface.BOLD);
    }
}

fun datePickerToLocalDate(datePicker: DatePicker): LocalDate {
    val year = datePicker.year
    val month = datePicker.month + 1
    val day = datePicker.dayOfMonth
    return LocalDate.of(year, month, day)
}
