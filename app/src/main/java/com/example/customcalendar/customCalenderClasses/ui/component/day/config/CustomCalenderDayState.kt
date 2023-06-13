package com.example.customcalendar.customCalenderClasses.ui.component.day.config

sealed interface CustomCalenderDayState {
    object CustomCalenderDaySelected : CustomCalenderDayState
    object CustomCalenderDayDefault : CustomCalenderDayState
}
