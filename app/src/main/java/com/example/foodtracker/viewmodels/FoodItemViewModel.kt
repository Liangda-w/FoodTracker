package com.example.foodtracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.foodtracker.database.FoodCategory
import com.example.foodtracker.database.FoodItem
import com.example.foodtracker.database.FoodItemDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * The ViewModel class is used to store data related to an app's UI,
 * and is also lifecycle aware, meaning that it responds to lifecycle events much like an activity or fragment does.
 * If lifecycle events such as screen rotation cause an activity or fragment to be destroyed and recreated,
 * the associated ViewModel won't need to be recreated.
 * This is not possible with accessing a DAO class directly,
 * so it's best practice to use ViewModel subclass to separate the responsibility of loading data from your activity or fragment.
 */
class FoodItemViewModel(
    private val foodItemDao: FoodItemDao,
) : ViewModel() {

    private var viewModelJob = Job()

    fun foodList(): Flow<List<FoodItem>> = foodItemDao.getAll()

    private fun insert(item: FoodItem) {
        viewModelScope.launch {
            foodItemDao.insert(item)
        }
    }

    private fun getNewFoodItemEntry(
        foodName: String,
        foodExpirationDate: LocalDate,
        category: FoodCategory,
        quantity: Int
    ): FoodItem {
        return FoodItem(
            foodName = foodName,
            foodExpirationDate = foodExpirationDate,
            category = category,
            quantity = quantity
        )
    }

    /**
     * Inserts the new Item into database.
     */
    fun addNewFoodItem(
        foodName: String,
        foodExpirationDate: LocalDate,
        category: FoodCategory,
        quantity: Int
    ) {
        val newItem = getNewFoodItemEntry(
            foodName,
            foodExpirationDate,
            category,
            quantity
        )
        insert(newItem)
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    fun insetItem(foodItem: FoodItem) {
        insert(foodItem)
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(item: FoodItem) {
        viewModelScope.launch {
            foodItemDao.delete(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

/**
 * As the ViewModel class  is meant to be lifecycle aware, it should be instantiated by an object that can respond to lifecycle events.
 * If you instantiate it directly in one of your fragments, then your fragment object will have to handle everything, including all the memory management, which is beyond the scope of what your app's code should do.
 * Instead, you can create a class, called a factory, that will instantiate view model objects for you.
 */
class FoodItemViewModelFactory(
    private val foodItemDao: FoodItemDao,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodItemViewModel::class.java)) {
            return FoodItemViewModel(foodItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}