package com.example.foodtracker.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "food_item_table")
data class FoodItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @NonNull
    @ColumnInfo(name = "food_name")
    val foodName: String,

    @NonNull
    @ColumnInfo(name = "food_expiration_date")
    val foodExpirationDate: LocalDate,

    @ColumnInfo(name = "category")
    val category: FoodCategory,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    )

enum class FoodCategory(val category: String) {
    DAIRY("Diary"),
    FRUITS("Fruits"),
    CEREALS_AND_LEGUMES("Cereals and Legumes"),
    MEAT("Meat"),
    CONFECTIONS("Confections"),
    VEGETABLES("Vegetables"),
    WATER("Water")
}