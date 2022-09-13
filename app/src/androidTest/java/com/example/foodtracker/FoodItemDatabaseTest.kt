package com.example.foodtracker

//import android.util.Log
//import androidx.room.Room
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import androidx.test.platform.app.InstrumentationRegistry
//import com.example.foodtracker.database.FoodCategory
//import com.example.foodtracker.database.FoodItem
//import com.example.foodtracker.database.FoodItemDao
//import com.example.foodtracker.database.FoodItemDatabase
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Test
//import org.junit.runner.RunWith
//import java.io.IOException
//import java.time.LocalDate
//
//@RunWith(AndroidJUnit4::class)
//class FoodItemDatabaseTest {
//    private lateinit var foodItemDao: FoodItemDao
//    private lateinit var db: FoodItemDatabase
//
//    @Before
//    fun createDb() {
//        val context = InstrumentationRegistry.getInstrumentation().targetContext
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
//        db = Room.inMemoryDatabaseBuilder(context, FoodItemDatabase::class.java)
//            // Allowing main thread queries, just for testing.
//            .allowMainThreadQueries()
//            .build()
//        foodItemDao = db.foodItemDao
//    }
//
//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun insertAndGetFoodItem() {
//        val date = LocalDate.parse("2022-10-01")
//        val foodItem = FoodItem(
//            foodName = "Apple",
//            foodExpirationDate = date,
//            category = FoodCategory.FRUITS,
//            quantity = 5
//        )
//        Log.d("Test", foodItem.toString())
//        foodItemDao.insert(foodItem)
//        Thread.sleep(5_000)
//
//        val foodItems = foodItemDao.getAll()
//        Log.d("Test", foodItems.toString())
//
//        Thread.sleep(5_000)
//        Assert.assertEquals(foodItems.value?.size, 1)
//    }
//}