package com.example.customcalendar.customCalenderClasses.model

import kotlinx.datetime.LocalDate

@JvmInline
value class CustomCalenderDay(val localDate: LocalDate)

fun LocalDate.toCustomerCalenderDay() = CustomCalenderDay(this)
