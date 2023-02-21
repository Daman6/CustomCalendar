package com.example.customcalendar.customCalenderClasses.ui.component.day.config

import androidx.compose.ui.graphics.Color

data class CustomCalenderDayColors(
    val textColor: Color, // Default Text Color
    val selectedTextColor: Color, // Selected Text Color
)

object CustomCalenderDayDefaultColors {

    fun defaultColors() = CustomCalenderDayColors(Color.White, Color.White)
}
