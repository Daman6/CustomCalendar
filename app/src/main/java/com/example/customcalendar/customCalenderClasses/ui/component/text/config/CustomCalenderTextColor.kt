package com.example.customcalendar.customCalenderClasses.ui.component.text.config

import androidx.compose.ui.graphics.Color

private val TitleTextColor = Color(0xFFD2827A)

data class CustomCalenderTextColor(
    val textColor: Color
)

internal object CustomCalenderTextColorDefaults {

    fun customCalenderTitleTextColor(color: Color = TitleTextColor) = CustomCalenderTextColor(
        textColor = color
    )

    fun customCalenderNormalTextColor(color: Color = TitleTextColor) = CustomCalenderTextColor(
        textColor = color
    )
}
