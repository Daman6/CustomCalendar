package com.example.customcalendar.model


import kotlinx.datetime.LocalDate


data class KalendarEvent(
    val date: LocalDate,
    val eventName: String,
    val eventDescription: String? = null,
)
