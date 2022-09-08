package com.example.foodtracker.data

import com.example.foodtracker.R
import com.example.foodtracker.model.FoodItem

class Datasource {

    fun loadFoodItems(): List<FoodItem> {
        return listOf<FoodItem>(
            FoodItem(R.string.apple, R.drawable.ic_fruit, 1),
            FoodItem(R.string.banana, R.drawable.ic_fruit, 1),
            FoodItem(R.string.orange, R.drawable.ic_fruit, 2),
            FoodItem(R.string.milk, R.drawable.ic_fruit, 3),
            FoodItem(R.string.eggs, R.drawable.ic_egg, 4),
            FoodItem(R.string.chicken, R.drawable.ic_meat, 6),
        )
    }
}