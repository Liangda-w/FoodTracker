package com.example.foodtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodtracker.DatePickerFragment.OnDatePickListener
import com.example.foodtracker.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve a binding object that allows you to refer to views by id name
        // Names are converted from snake case to camel case.
        // For example, a View with the id word_one is referenced as binding.wordOne
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.datePickerButton.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            datePickerFragment.setListener(object : OnDatePickListener {
                override fun onDateSet(date: String?) {
                    binding.datePickerResult.setText(date)
                }
            })
            datePickerFragment.show(supportFragmentManager, "datePicker")
        }

    }
}