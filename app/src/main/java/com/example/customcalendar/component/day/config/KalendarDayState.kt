package com.example.customcalendar.component.day.config

sealed interface KalendarDayState {
    object KalendarDaySelected : KalendarDayState
    object KalendarDayDefault : KalendarDayState
}
