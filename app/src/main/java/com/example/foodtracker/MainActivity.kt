package com.example.foodtracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtracker.adapter.ItemAdapter
import com.example.foodtracker.data.Datasource
import com.example.foodtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize data.
        val myDataset = Datasource().loadFoodItems()

        recyclerView = binding.recyclerView
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        binding.addFloatingActionButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            this.startActivity(intent)
        }
    }
}