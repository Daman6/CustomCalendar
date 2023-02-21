package com.example.customcalendar.kalendarClasses.ui.component.day.config

sealed interface KalendarDayState {
    object KalendarDaySelected : KalendarDayState
    object KalendarDayDefault : KalendarDayState
}
