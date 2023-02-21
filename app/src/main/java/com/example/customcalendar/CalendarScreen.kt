package com.example.customcalendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.customcalendar.kalendarClasses.model.KalendarEvent
import com.example.customcalendar.kalendarClasses.model.KalendarType
import com.example.customcalendar.kalendarClasses.ui.color.KalendarColors
import com.example.customcalendar.kalendarClasses.ui.color.KalendarThemeColor
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayColors
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayDefaultColors
import com.example.customcalendar.kalendarClasses.ui.component.header.config.KalendarHeaderConfig
import com.example.customcalendar.kalendarClasses.ui.firey.KalendarFirey

import kotlinx.datetime.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KalendarDemo() {

    Column(
        Modifier
            .wrapContentSize()
            .background(Color.LightGray)
    ) {

        Kalendar(
            kalendarType = KalendarType.Firey,
            modifier = Modifier
                .border(BorderStroke(2.dp, Color.White)),
            onCurrentDayClick = { calender, listOfEvents ->
                val cal = calender
                Log.e("ddjndn","Selected dated "+cal.localDate.toString())
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
                kalendarHeaderConfig = kalendarHeaderConfig
            )
        }
        else -> {
        }
    }
}

