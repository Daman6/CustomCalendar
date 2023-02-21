package com.example.customcalendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.customcalendar.customCalenderClasses.model.CustomCalenderEvent
import com.example.customcalendar.customCalenderClasses.ui.color.CustomCalenderColors
import com.example.customcalendar.customCalenderClasses.ui.color.CustomCalenderThemeColor
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.KalendarDayColors
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.KalendarDayDefaultColors
import com.example.customcalendar.customCalenderClasses.ui.component.header.config.KalendarHeaderConfig
import com.example.customcalendar.customCalenderClasses.ui.firey.CustomCalenderImpl

import kotlinx.datetime.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalenderLib() {

    Column(
        Modifier
            .wrapContentSize()
            .background(Color.LightGray)
    ) {

        CustomCalender(
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
fun CustomCalender(
    modifier: Modifier = Modifier,
    customCalenderEvents: List<CustomCalenderEvent> = emptyList(),
    customCalenderThemeColors: List<CustomCalenderThemeColor> = CustomCalenderColors.defaultColors(),
    onCurrentDayClick: (com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay, List<CustomCalenderEvent>) -> Unit = { _, _ -> },
    kalendarDayColors: KalendarDayColors = KalendarDayDefaultColors.defaultColors(),
    kalendarHeaderConfig: KalendarHeaderConfig? = null,
    takeMeToDate: LocalDate? = null,
) {
    if (customCalenderThemeColors.isEmpty() || customCalenderThemeColors.count() < 12) throw Exception("KalendarThemeColor cannot be null or less than 12, If you want to use same color accors months pass kalendarThemeColor = KalendarThemeColor(values)")

    CustomCalenderImpl(
        modifier = modifier.wrapContentHeight(),
        customCalenderEvents = customCalenderEvents,
        onCurrentDayClick = onCurrentDayClick,
        kalendarDayColors = kalendarDayColors,
        customCalenderThemeColors = customCalenderThemeColors,
        takeMeToDate = takeMeToDate,
        kalendarHeaderConfig = kalendarHeaderConfig
    )
}

