package com.example.customcalendar.customCalenderClasses.ui.component.day

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customcalendar.customCalenderClasses.model.CustomCalenderEvent
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.CustomCalenderDayColors
import com.example.customcalendar.customCalenderClasses.ui.component.day.config.CustomCalenderDayState
import com.example.customcalendar.customCalenderClasses.ui.component.text.CustomCalenderNormalText
import com.example.customcalendar.customCalenderClasses.utils.Constant

import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CustomCalenderDay(
    customCalenderDay: com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay,
    selectedCustomCalenderDay: kotlinx.datetime.LocalDate,
    customCalenderDayColors: CustomCalenderDayColors,
    dotColor: Color,
    dayBackgroundColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    textSize: TextUnit = 12.sp,
    customCalenderEvents: List<CustomCalenderEvent> = emptyList(),
    isCurrentDay: Boolean = false,
    onCurrentDayClick: (com.example.customcalendar.customCalenderClasses.model.CustomCalenderDay, List<CustomCalenderEvent>) -> Unit = { _, _ -> },

    ) {
    val customCalenderDayState = getCustomCalenderDayState(selectedCustomCalenderDay, customCalenderDay.localDate)
    val bgColor = getBackgroundColor(customCalenderDayState, dayBackgroundColor)
    val textColor = getTextColor(customCalenderDayState, customCalenderDayColors)
    val weight = getTextWeight(customCalenderDayState)
    val border = getBorder(isCurrentDay)

    Column(
        modifier = modifier
            .border(border = border, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onCurrentDayClick(customCalenderDay, customCalenderEvents) }
            .size(size = size)
            .background(color = bgColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CustomCalenderNormalText(
            text = customCalenderDay.localDate.dayOfMonth.toString(),
            modifier = Modifier,
            fontWeight = weight,
            textColor = textColor,
            textSize = textSize,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            val customCalenderEventForDay = customCalenderEvents.filter { it.date == customCalenderDay.localDate }
            if (customCalenderEventForDay.isNotEmpty()) {
                val dayEvents = if (customCalenderEventForDay.count() > 3) customCalenderEventForDay.take(3) else customCalenderEventForDay
                dayEvents.forEachIndexed { index, _ ->
                    CustomCalenderDots(
                        modifier = Modifier, index = index, size = size, color = dotColor
                    )
                }
            }
        }
    }
}

@Composable
fun CustomCalenderDots(
    modifier: Modifier = Modifier,
    index: Int,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .padding(horizontal = 0.dp)
            .clip(shape = CircleShape)
            .background(
                color = color.copy(alpha = index.plus(1) * 0.3F)
            )
            .size(size = size.div(12))
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
    customCalenderDayState: CustomCalenderDayState,
    dayBackgroundColor: Color
) = if (customCalenderDayState is CustomCalenderDayState.CustomCalenderDaySelected) {
    Constant.selectedDotColor
} else {
    Color.Transparent
}

private fun getTextColor(
    customCalenderDayState: CustomCalenderDayState,
    customCalenderDayColors: CustomCalenderDayColors,
): Color = if (customCalenderDayState is CustomCalenderDayState.CustomCalenderDaySelected) {
    customCalenderDayColors.selectedTextColor
} else {
    customCalenderDayColors.textColor
}
