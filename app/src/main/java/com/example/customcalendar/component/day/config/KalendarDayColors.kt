package com.example.customcalendar.component.day.config

import androidx.compose.ui.graphics.Color

data class KalendarDayColors(
    val textColor: Color, // Default Text Color
    val selectedTextColor: Color, // Selected Text Color
)

object KalendarDayDefaultColors {

    fun defaultColors() = KalendarDayColors(Color.White, Color.White)
}
