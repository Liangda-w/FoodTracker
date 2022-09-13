package com.example.foodtracker.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object FoodItemTypeConverters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String): LocalDate = formatter.parse(value, LocalDate::from)

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate): String = date.format(formatter)
}