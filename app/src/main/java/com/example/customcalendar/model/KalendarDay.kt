package com.example.customcalendar.model

import kotlinx.datetime.LocalDate

@JvmInline
value class KalendarDay(val localDate: LocalDate)

fun LocalDate.toKalendarDay() = KalendarDay(this)
