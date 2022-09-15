package com.example.foodtracker.utils

import android.widget.Spinner
import com.example.foodtracker.database.FoodCategory

fun categorySpinnerToEnum(spinner: Spinner): FoodCategory {
    val category = spinner.selectedItem.toString()
    var categoryEnum = FoodCategory.DAIRY
    for (it in FoodCategory.values()) {
        if (category == it.category) {
            categoryEnum = it
        }
    }
    return categoryEnum
}