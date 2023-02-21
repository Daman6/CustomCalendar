package com.example.customcalendar.kalendarClasses.model

import kotlinx.datetime.LocalDate

@JvmInline
value class KalendarDay(val localDate: LocalDate)

fun LocalDate.toKalendarDay() = com.example.customcalendar.kalendarClasses.model.KalendarDay(this)
