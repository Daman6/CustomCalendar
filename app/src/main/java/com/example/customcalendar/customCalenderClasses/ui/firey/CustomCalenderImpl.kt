
package com.example.customcalendar.customCalenderClasses.ui.firey

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.customcalendar.customCalenderClasses.model.CustomCalenderEvent
import com.example.customcalendar.customCalenderClasses.model.toCustomerCalenderDay
import com.example.customcalendar.customCalenderClasses.ui.color.CustomCalenderThemeColor
import com.example.customcalendar.customCalenderClasses.ui.component.day.KalendarDay
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.KalendarDayColors
import com.example.customcalendar.customCalenderClasses.ui.component.header.KalendarHeader
import com.example.customcalendar.customCalenderClasses.ui.component.header.config.KalendarHeaderConfig
import com.example.customcalendar.customCalenderClasses.ui.component.text.KalendarNormalText
import com.example.customcalendar.customCalenderClasses.ui.component.text.config.KalendarTextColor
import com.example.customcalendar.customCalenderClasses.ui.component.text.config.KalendarTextConfig
import com.example.customcalendar.customCalenderClasses.ui.component.text.config.KalendarTextSize
import com.himanshoe.kalendar.utils.Constant
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.todayIn

val WeekDays = listOf("M", "T", "W", "T", "F", "S", "S")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalenderImpl(
    modifier: Modifier = Modifier,
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    customCalenderThemeColors: List<CustomCalenderThemeColor>,
    customCalenderEvents: List<CustomCalenderEvent> = emptyList(),
    onCurrentDayClick: (com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay, List<CustomCalenderEvent>) -> Unit = { _, _ -> },
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val displayedMonth = remember {
        mutableStateOf(currentDay.month)
    }
    val displayedYear = remember {
        mutableStateOf(currentDay.year)
    }
    val currentMonth = displayedMonth.value
    val currentYear = displayedYear.value

    val daysInMonth = currentMonth.minLength()
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString() else currentMonth.value.toString()
    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()
    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }
    val newKalenderHeaderConfig = KalendarHeaderConfig(
        kalendarTextConfig = KalendarTextConfig(
            kalendarTextSize = KalendarTextSize.SubTitle,
            kalendarTextColor = KalendarTextColor(
                customCalenderThemeColors[currentMonth.value.minus(1)].headerTextColor,
            )
        )
    )

    Column(
        modifier = modifier
            .background(
                //color changed for the background
                color = Constant.BackgroundColor
            )
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        KalendarHeader(
            modifier = Modifier,
            month = displayedMonth.value,
            onPreviousClick = {
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
            },
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
            },
            year = displayedYear.value,
            kalendarHeaderConfig = kalendarHeaderConfig ?: newKalenderHeaderConfig
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            columns = GridCells.Fixed(7),
            content = {
                items(WeekDays) {
                    KalendarNormalText(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textColor = kalendarDayColors.textColor,
                    )
                }
                items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                    if (it > 0) {
                        val day = getGeneratedDay(it, currentMonth, currentYear)
                        val isCurrentDay = day == currentDay
                        KalendarDay(
                            customCalenderDay = day.toCustomerCalenderDay(),
                            modifier = Modifier.padding(9.dp),
                            customCalenderEvents = customCalenderEvents,
                            isCurrentDay = isCurrentDay,
                            onCurrentDayClick = { kalendarDay, events ->
                                selectedKalendarDate.value = kalendarDay.localDate
                                onCurrentDayClick(kalendarDay, events)
                            },
                            selectedKalendarDay = selectedKalendarDate.value,
                            kalendarDayColors = kalendarDayColors,
                            dotColor = customCalenderThemeColors[currentMonth.value.minus(1)].headerTextColor,
                            dayBackgroundColor = customCalenderThemeColors[currentMonth.value.minus(1)].dayBackgroundColor,
                        )
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalenderImpl(
    modifier: Modifier = Modifier,
    customCalenderEvents: List<CustomCalenderEvent> = emptyList(),
    onCurrentDayClick: (com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay, List<CustomCalenderEvent>) -> Unit = { _, _ -> },
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    customCalenderThemeColor: CustomCalenderThemeColor,
    kalendarHeaderConfig: KalendarHeaderConfig? = null
) {
    val currentDay = takeMeToDate ?: Clock.System.todayIn(TimeZone.currentSystemDefault())
    val displayedMonth = remember {
        mutableStateOf(currentDay.month)
    }
    val displayedYear = remember {
        mutableStateOf(currentDay.year)
    }
    val currentMonth = displayedMonth.value
    val currentYear = displayedYear.value

    val daysInMonth = currentMonth.minLength()
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString() else currentMonth.value.toString()
    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()
    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }
    val newKalenderHeaderConfig = KalendarHeaderConfig(
        KalendarTextConfig(
            kalendarTextColor = KalendarTextColor(
                customCalenderThemeColor.headerTextColor
            ),
            kalendarTextSize = KalendarTextSize.SubTitle
        )
    )

    Column(
        modifier = modifier
            .background(
                color = customCalenderThemeColor.backgroundColor
            )
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        KalendarHeader(
            modifier = Modifier,
            month = displayedMonth.value,
            onPreviousClick = {
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
            },
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
            },
            year = displayedYear.value,
            kalendarHeaderConfig = kalendarHeaderConfig ?: newKalenderHeaderConfig
        )

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(7),
            content = {
                items(WeekDays) {
                    KalendarNormalText(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textColor = kalendarDayColors.textColor,
                    )
                }
                items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                    if (it > 0) {
                        val day = getGeneratedDay(it, currentMonth, currentYear)
                        val isCurrentDay = day == currentDay
                        KalendarDay(
                            customCalenderDay = day.toCustomerCalenderDay(),
                            modifier = Modifier,
                            customCalenderEvents = customCalenderEvents,
                            isCurrentDay = isCurrentDay,
                            onCurrentDayClick = { kalendarDay, events ->
                                selectedKalendarDate.value = kalendarDay.localDate
                                onCurrentDayClick(kalendarDay, events)
                            },
                            selectedKalendarDay = selectedKalendarDate.value,
                            kalendarDayColors = kalendarDayColors,
                            dotColor = customCalenderThemeColor.headerTextColor,
                            dayBackgroundColor = customCalenderThemeColor.dayBackgroundColor,
                        )
                    }
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getInitialDayOfMonth(firstDayOfMonth: DayOfWeek) = -(firstDayOfMonth.value).minus(2)

@RequiresApi(Build.VERSION_CODES.O)
private fun getGeneratedDay(day: Int, currentMonth: Month, currentYear: Int): LocalDate {
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0${currentMonth.value}" else currentMonth.value.toString()
    val newDay = if (day.toString().length == 1) "0$day" else day
    return "$currentYear-$monthValue-$newDay".toLocalDate()
}
