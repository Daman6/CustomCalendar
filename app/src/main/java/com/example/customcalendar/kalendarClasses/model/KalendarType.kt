package com.example.customcalendar.kalendarClasses.model

sealed interface KalendarType {
    object Firey : KalendarType
    data class Oceanic(val showWeekDays: Boolean = true) : KalendarType
}
