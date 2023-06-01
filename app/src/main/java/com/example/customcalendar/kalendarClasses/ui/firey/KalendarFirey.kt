
package com.example.customcalendar.kalendarClasses.ui.firey

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.customcalendar.kalendarClasses.model.KalendarEvent
import com.example.customcalendar.kalendarClasses.model.toKalendarDay
import com.example.customcalendar.kalendarClasses.ui.color.KalendarThemeColor
import com.example.customcalendar.kalendarClasses.ui.component.day.KalendarDay
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayColors
import com.example.customcalendar.kalendarClasses.ui.component.header.KalendarHeader
import com.example.customcalendar.kalendarClasses.ui.component.header.config.KalendarHeaderConfig
import com.example.customcalendar.kalendarClasses.ui.component.text.KalendarNormalText
import com.example.customcalendar.kalendarClasses.ui.component.text.config.KalendarTextColor
import com.example.customcalendar.kalendarClasses.ui.component.text.config.KalendarTextConfig
import com.example.customcalendar.kalendarClasses.ui.component.text.config.KalendarTextSize
import com.himanshoe.kalendar.utils.Constant
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.todayIn
import java.text.SimpleDateFormat
import java.util.*

val WeekDays = listOf("M", "T", "W", "T", "F", "S", "S")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KalendarFirey(
    modifier: Modifier = Modifier,
    takeMeToDate: LocalDate?,
    kalendarDayColors: KalendarDayColors,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    kalendarThemeColors: List<KalendarThemeColor>,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    onCurrentDayClick: (com.example.customcalendar.kalendarClasses.model.KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
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
//    val daysInMonth2 = if(currentMonth.minLength().equals(30)) currentMonth.minLength()+1 else currentMonth.minLength()-1
    val monthValue =
        if (currentMonth.value.toString().length == 1) "0" + currentMonth.value.toString() else currentMonth.value.toString()
//    val monthValue2 =(currentMonth.value + 1 ).toString()
    val startDayOfMonth = "$currentYear-$monthValue-01".toLocalDate()
    val ENDDayOfMonth = "$currentYear-$monthValue-$daysInMonth".toLocalDate()
    Log.e("startDayOfMonth",startDayOfMonth.toString())
    Log.e("ENDDayOfMonth", ENDDayOfMonth.toString())
    val firstDayOfMonth = startDayOfMonth.dayOfWeek
    val selectedKalendarDate = remember { mutableStateOf(currentDay) }
    val newKalenderHeaderConfig = KalendarHeaderConfig(
        kalendarTextConfig = KalendarTextConfig(
            kalendarTextSize = KalendarTextSize.SubTitle,
            kalendarTextColor = KalendarTextColor(
                kalendarThemeColors[currentMonth.value.minus(1)].headerTextColor,
            )
        )
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .border(BorderStroke(2.dp, Color.LightGray.copy(0.6f)))
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
            onPreviousClick ={
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
                onPreviousClick()
            },
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
                onNextClick()
            },
            year = displayedYear.value,
            kalendarHeaderConfig = kalendarHeaderConfig ?: newKalenderHeaderConfig
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            columns = GridCells.Fixed(7),
            content = {
                items(WeekDays) {
                    KalendarNormalText(
                        text = it,
                        fontWeight = FontWeight.Normal,
                        textColor = Color.LightGray,
                    )
                }
                items((getInitialDayOfMonth(firstDayOfMonth)..daysInMonth).toList()) {
                    if (it > 0) {
                        val day = getGeneratedDay(it, currentMonth, currentYear)
                        val isCurrentDay = day == currentDay
                        KalendarDay(
                            kalendarDay = day.toKalendarDay(),
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                            kalendarEvents = kalendarEvents,
                            isCurrentDay = isCurrentDay,
                            onCurrentDayClick = { kalendarDay, events ->
                                selectedKalendarDate.value = kalendarDay.localDate
                                onCurrentDayClick(kalendarDay, events)
                            },
                            selectedKalendarDay = selectedKalendarDate.value,
                            kalendarDayColors = kalendarDayColors,
                            dotColor = kalendarThemeColors[currentMonth.value.minus(1)].headerTextColor,
                            dayBackgroundColor = kalendarThemeColors[currentMonth.value.minus(1)].dayBackgroundColor,
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

