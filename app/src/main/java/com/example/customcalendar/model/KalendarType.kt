package com.example.customcalendar.model

sealed interface KalendarType {
    object Firey : KalendarType
    data class Oceanic(val showWeekDays: Boolean = true) : KalendarType
}
