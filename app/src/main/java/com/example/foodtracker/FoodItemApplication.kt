package com.example.foodtracker

import android.app.Application
import com.example.foodtracker.database.FoodItemDatabase

/**
 * provide a custom subclass of the Application class,
 * and create a lazy property that will hold the result of getDatabase().
 */
class FoodItemApplication : Application() {
    val database: FoodItemDatabase by lazy { FoodItemDatabase.getDatabase(this) }
}
