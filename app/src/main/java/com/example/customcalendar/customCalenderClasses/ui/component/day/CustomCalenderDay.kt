package com.example.customcalendar.customCalenderClasses.ui.component.day

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay
import com.example.customcalendar.customCalenderClasses.model.CustomCalenderEvent
import com.example.customcalendar.customCalenderClasses.model.toCustomerCalenderDay
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.CustomCalenderDayColors
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.CustomCalenderDayState
import com.example.customcalendar.customCalenderClasses.ui.component.text.CustomCalenderNormalText
import com.example.customcalendar.customCalenderClasses.ui.component.text.CustomCalenderTextDisabled
import com.example.customcalendar.customCalenderClasses.utils.Constant
import kotlinx.datetime.Clock

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalenderDay(
    customCalenderDay: com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay,
    selectedCustomCalenderDay: kotlinx.datetime.LocalDate,
    dayBackgroundColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    textSize: TextUnit = 12.sp,
    customCalenderEvents: List<CustomCalenderEvent> = emptyList(),
    onCurrentDayClick: (com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay, List<CustomCalenderEvent>) -> Unit = { _, _ -> },

    ) {
    val customCalenderDayState =
        getCustomCalenderDayState(selectedCustomCalenderDay, customCalenderDay.localDate)
    val bgColor = getBackgroundColor(customCalenderDayState, dayBackgroundColor)
    val doColor = getDotColor(customCalenderEvents, customCalenderDay)
    val weight = getTextWeight(customCalenderDayState)
    val customCalenderEventForDay =
        customCalenderEvents.filter { it.date == customCalenderDay.localDate }
    val textColor = getTextColor(customCalenderEventForDay)



    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                if (customCalenderEventForDay.isNotEmpty()) {
                    onCurrentDayClick(customCalenderDay, customCalenderEvents)
                } else{

                }
              },
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = modifier
                .size(33.dp)
                .border(1.dp, bgColor, CircleShape)
                .padding(4.dp)
                .clip(CircleShape)
                .background(bgColor),

        ) {
            if (customCalenderEventForDay.isNotEmpty()) {
                CustomCalenderNormalText(
                    text = customCalenderDay.localDate.dayOfMonth.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = weight,
                    textColor = textColor,
                    textSize = textSize,
                )
            }
            else {
                CustomCalenderTextDisabled(
                    text = customCalenderDay.localDate.dayOfMonth.toString(),
                    modifier = Modifier.align(Alignment.Center),
                    fontWeight = weight,
                    textColor = textColor,
                    textSize = textSize,
                )
            }
        }
        Spacer(modifier = modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            if (customCalenderEventForDay.isNotEmpty()) {
                val dayEvents =
                    if (customCalenderEventForDay.count() > 3) customCalenderEventForDay.take(3) else customCalenderEventForDay
                dayEvents.forEachIndexed { index, it ->
                    CustomCalenderDots(
                        modifier = Modifier, index = 1, size = size, color = doColor
                    )
                }
            }
        }

    }

}

@Composable
fun CustomCalenderDots(
    modifier: Modifier = Modifier, index: Int, size: Dp, color: Color
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                color = color.copy(alpha = index.plus(1) * 0.3F)
            )
            .size(
                size = size.div(18)
            )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun getCustomCalenderDayState(selectedDate: LocalDate, currentDay: LocalDate) =
    when (selectedDate) {
        currentDay -> CustomCalenderDayState.CustomCalenderDaySelected
        else -> CustomCalenderDayState.CustomCalenderDayDefault
    }

private fun getBorder(isCurrentDay: Boolean) = BorderStroke(
    width = if (isCurrentDay) 1.dp else 0.dp,
    color = if (isCurrentDay) Color.White else Color.Transparent,
)

private fun getTextWeight(customCalenderDayState: CustomCalenderDayState) =
    if (customCalenderDayState is CustomCalenderDayState.CustomCalenderDaySelected) {
        FontWeight.Bold
    } else {
        FontWeight.SemiBold
    }

private fun getBackgroundColor(
    customCalenderDayState: CustomCalenderDayState, dayBackgroundColor: Color
) = if (customCalenderDayState is CustomCalenderDayState.CustomCalenderDaySelected) {
    Constant.selectedDotColor
} else {
    Color.Transparent
}


private fun getDotColor(
    customCalenderEvents: List<CustomCalenderEvent>, customCalenderDay: CustomCalenderDay
): Color {
    val customCalenderEventForDay =
        customCalenderEvents.filter { it.date == customCalenderDay.localDate }
    val dayEvents =
        if (customCalenderEventForDay.count() > 3) customCalenderEventForDay.take(3) else customCalenderEventForDay
    dayEvents.forEachIndexed { index, it ->
        if (it.date > Clock.System.todayIn(TimeZone.currentSystemDefault())) {
            return Color.Red
        } else {
            return Color.Gray
        }
    }
    return Color.Red
}

private fun getTextColor(
    customCalenderEventForDay: List<CustomCalenderEvent>
) = if (customCalenderEventForDay.isNotEmpty()){
        Color.White
    }else{
        Color.Gray
    }


//private fun getTextColor(
//    customCalenderDayState: CustomCalenderDayState,
//    customCalenderDayColors: CustomCalenderDayColors,
//): Color = if (customCalenderDayState is CustomCalenderDayState.CustomCalenderDaySelected) {
//    customCalenderDayColors.selectedTextColor
//} else {
//    customCalenderDayColors.textColor
//}
