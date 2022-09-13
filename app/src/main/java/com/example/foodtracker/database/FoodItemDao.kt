package com.example.foodtracker.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * DAO is where you would include functions for reading and manipulating data.
 * Calling a function on the DAO is the equivalent of performing a SQL command on the database.
 */
@Dao
interface FoodItemDao {
    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(foodItem: FoodItem)

    @Update
    suspend fun update(foodItem: FoodItem)

    @Delete
    suspend fun delete(foodItem: FoodItem)

    @Query("SELECT * FROM food_item_table WHERE id=:key")
    fun getOne(key: Long): Flow<FoodItem>

    @Query("SELECT * FROM food_item_table ORDER BY food_expiration_date ASC")
    fun getAll(): Flow<List<FoodItem>>
}