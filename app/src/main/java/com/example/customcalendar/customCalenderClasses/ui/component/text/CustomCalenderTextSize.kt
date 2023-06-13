package com.example.customcalendar.customCalenderClasses.ui.component.text


import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

sealed class CustomCalenderTextSize(val size: TextUnit) {
    object Title : CustomCalenderTextSize(32.sp)
    object SubTitle : CustomCalenderTextSize(24.sp)
    object Normal : CustomCalenderTextSize(16.sp)
    data class Custom(val textUnit: TextUnit) : CustomCalenderTextSize(textUnit)
}
