package com.example.customcalendar.customCalenderClasses.ui.component.day.config

sealed interface KalendarDayState {
    object KalendarDaySelected : KalendarDayState
    object KalendarDayDefault : KalendarDayState
}
