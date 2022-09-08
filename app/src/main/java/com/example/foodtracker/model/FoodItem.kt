package com.example.foodtracker.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FoodItem(
    @StringRes val stringResourceId: Int,
    @DrawableRes val iconResourceId: Int,
    val expirationIn: Int
)