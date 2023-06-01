package com.example.customcalendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.customcalendar.kalendarClasses.model.KalendarDay
import com.example.customcalendar.kalendarClasses.model.KalendarEvent
import com.example.customcalendar.kalendarClasses.model.KalendarType
import com.example.customcalendar.kalendarClasses.ui.color.KalendarColors
import com.example.customcalendar.kalendarClasses.ui.color.KalendarThemeColor
import com.example.customcalendar.kalendarClasses.ui.component.day.KalendarDots
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayColors
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayDefaultColors
import com.example.customcalendar.kalendarClasses.ui.component.header.config.KalendarHeaderConfig
import com.example.customcalendar.kalendarClasses.ui.firey.KalendarFirey
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone

import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.milliseconds


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KalendarDemo() {
    val currentDay = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val displayedMonth = remember {
        mutableStateOf(currentDay.month)
    }
    val displayedYear = remember {
        mutableStateOf(currentDay.year)
    }


    Column(
        Modifier
            .wrapContentSize()
            .background(color = colorResource(id = R.color.border_background))
    ) {

        Kalendar(
            kalendarType = KalendarType.Firey,
            modifier = Modifier
                .padding(10.dp)
//                .clip(RoundedCornerShape(5.dp))
//                .border(BorderStroke(2.dp, Color.LightGray.copy(0.6f))),
            , onCurrentDayClick = { calender, listOfEvents ->
                val caldate = listOfEvents.filter { it.date == calender.localDate }
                if (caldate.isNotEmpty()) {
                    val cal = calender
                    val dateString = cal.localDate.toString()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd a", Locale.US)
                    val dateWithAM = dateFormat.parse(dateString + " AM")
                    val dateWithPM = dateFormat.parse(dateString + " PM")
                    val Starttimestamp = dateWithAM.time / 1000
                    val Endtimestamp = dateWithPM.time / 1000
                    Log.e("ddjndn", "Start time of selected date " + Starttimestamp.toString())
                    Log.e("ddjndn", "END time of selected date " + Endtimestamp.toString())
                    Log.e("ddjndn", "Selected dated " + cal.localDate.toString())
                }

            },
            kalendarEvents = listOf(
                KalendarEvent(LocalDate(2023, 3, 3), "Birthday"),
                KalendarEvent(LocalDate(2023, 3, 3), "Birthday"),
                KalendarEvent(LocalDate(2023, 3, 25), "Birthday"),
                KalendarEvent(LocalDate(2022, 10, 25), "Birthday"),
                KalendarEvent(LocalDate(2022, 10, 28), "Party"),
                KalendarEvent(LocalDate(2022, 10, 29), "Club"),
            ),
            onNextClick = {
                if (displayedMonth.value.value == 12) {
                    displayedYear.value = displayedYear.value.plus(1)
                }
                displayedMonth.value = displayedMonth.value.plus(1)
                val cm = displayedMonth.value
                val cy = displayedYear.value
                Log.e("TimestampForStartDate",getStartAndEndDate(cm,cy).first.toString())
            },
            onPreviousClick = {
                if (displayedMonth.value.value == 1) {
                    displayedYear.value = displayedYear.value.minus(1)
                }
                displayedMonth.value = displayedMonth.value.minus(1)
                val cm = displayedMonth.value
                val cy = displayedYear.value
                Log.e("TimestampForEndDate",getStartAndEndDate(cm,cy).second.toString())

            }

        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Kalendar(
    modifier: Modifier = Modifier,
    kalendarType: KalendarType = KalendarType.Oceanic(true),
    kalendarEvents: List<KalendarEvent> = emptyList(),
    kalendarThemeColors: List<KalendarThemeColor> = KalendarColors.defaultColors(),
    onCurrentDayClick: (com.example.customcalendar.kalendarClasses.model.KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },
    kalendarDayColors: KalendarDayColors = KalendarDayDefaultColors.defaultColors(),
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    takeMeToDate: LocalDate? = null,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
) {
    if (kalendarThemeColors.isEmpty() || kalendarThemeColors.count() < 12) throw Exception("KalendarThemeColor cannot be null or less than 12, If you want to use same color accors months pass kalendarThemeColor = KalendarThemeColor(values)")

    when (kalendarType) {
        KalendarType.Firey -> {
            KalendarFirey(
                modifier = modifier.wrapContentHeight(),
                kalendarEvents = kalendarEvents,
                onCurrentDayClick = onCurrentDayClick,
                kalendarDayColors = kalendarDayColors,
                kalendarThemeColors = kalendarThemeColors,
                takeMeToDate = takeMeToDate,
                kalendarHeaderConfig = kalendarHeaderConfig,
                onPreviousClick = { onPreviousClick() },
                onNextClick = { onNextClick() },
            )
        }
        else -> {
        }
    }
}


fun getStartAndEndDate(displayedMonth: Month, displayedYear: Int): Pair<Long, Long> {
    val cm = displayedMonth
    val cy = displayedYear

    val dim = cm.minLength()
    val mv =
        if (cm.value.toString().length == 1) "0" + cm.value.toString() else cm.value.toString()
    val startOfMonth = "$cy-$mv-01".toLocalDate()
    val endOfMonth = "$cy-$mv-$dim".toLocalDate()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd a", Locale.US)
    val dateWithAM = dateFormat.parse("$startOfMonth AM")
    val dateWithPM = dateFormat.parse("$endOfMonth PM")
    val Starttimestamp = dateWithAM!!.time / 1000
    val Endtimestamp = dateWithPM!!.time / 1000
    return Pair(Starttimestamp, Endtimestamp)
}

