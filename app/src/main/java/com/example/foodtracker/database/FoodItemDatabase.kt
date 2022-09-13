package com.example.foodtracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FoodItem::class], version = 1)
@TypeConverters(FoodItemTypeConverters::class)
abstract class FoodItemDatabase : RoomDatabase() {
    /**
     * Associate with DAO
     */
    abstract fun foodItemDao(): FoodItemDao

    /**
     * When using an AppDatabase class, you want to ensure that only one instance of the database exists to prevent race conditions or other potential issues.
     * The instance is stored in the companion object
     * Companion object allows clients to access the methods for creating or gettting the DB without instantiating the class
     */
    companion object {
        /** Volatile make sure the value is always up-to-date and the same to all execution threads
         * Value of volatile variable will never be cached and all writes and reads will be done to and from the main memory
         * so changes done to one instance are visable to all other threads immediately
         */
        @Volatile
        private var INSTANCE: FoodItemDatabase? = null

        /**
         * either return the existing instance of the database (if it already exists)
         * or create the database for the first time if needed.
         */
        fun getDatabase(context: Context): FoodItemDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        FoodItemDatabase::class.java,
                        "food_item_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}