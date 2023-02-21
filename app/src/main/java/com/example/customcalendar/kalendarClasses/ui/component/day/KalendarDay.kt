package com.example.customcalendar.kalendarClasses.ui.component.day

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
import com.example.customcalendar.kalendarClasses.model.KalendarEvent
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayColors
import com.example.customcalendar.kalendarClasses.ui.component.day.config.KalendarDayState
import com.example.customcalendar.kalendarClasses.ui.component.text.KalendarNormalText

import com.himanshoe.kalendar.utils.Constant
import kotlinx.datetime.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun KalendarDay(
    kalendarDay: com.example.customcalendar.kalendarClasses.model.KalendarDay,
    selectedKalendarDay: kotlinx.datetime.LocalDate,
    kalendarDayColors: KalendarDayColors,
    dotColor: Color,
    dayBackgroundColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 38.dp,
    textSize: TextUnit = 12.sp,
    kalendarEvents: List<KalendarEvent> = emptyList(),
    isCurrentDay: Boolean = false,
    onCurrentDayClick: (com.example.customcalendar.kalendarClasses.model.KalendarDay, List<KalendarEvent>) -> Unit = { _, _ -> },

    ) {
    val kalendarDayState = getKalendarDayState(selectedKalendarDay, kalendarDay.localDate)
    val bgColor = getBackgroundColor(kalendarDayState, dayBackgroundColor)
    val textColor = getTextColor(kalendarDayState, kalendarDayColors)
    val weight = getTextWeight(kalendarDayState)
    val border = getBorder(isCurrentDay)

    Column(
        modifier = modifier
            .border(border = border, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) { onCurrentDayClick(kalendarDay, kalendarEvents) }
            .size(size = size)
            .background(color = bgColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        KalendarNormalText(
            text = kalendarDay.localDate.dayOfMonth.toString(),
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
            val kalendarEventForDay = kalendarEvents.filter { it.date == kalendarDay.localDate }
            if (kalendarEventForDay.isNotEmpty()) {
                val dayEvents = if (kalendarEventForDay.count() > 3) kalendarEventForDay.take(3) else kalendarEventForDay
                dayEvents.forEachIndexed { index, _ ->
                    KalendarDots(
                        modifier = Modifier, index = index, size = size, color = dotColor
                    )
                }
            }
        }
    }
}

@Composable
fun KalendarDots(
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
private fun getKalendarDayState(selectedDate: LocalDate, currentDay: LocalDate) =
    when (selectedDate) {
        currentDay -> KalendarDayState.KalendarDaySelected
        else -> KalendarDayState.KalendarDayDefault
    }

private fun getBorder(isCurrentDay: Boolean) = BorderStroke(
    width = if (isCurrentDay) 1.dp else 0.dp,
    color = if (isCurrentDay) Color.White else Color.Transparent,
)

private fun getTextWeight(kalendarDayState: KalendarDayState) =
    if (kalendarDayState is KalendarDayState.KalendarDaySelected) {
        FontWeight.Bold
    } else {
        FontWeight.SemiBold
    }

private fun getBackgroundColor(
    kalendarDayState: KalendarDayState,
    dayBackgroundColor: Color
) = if (kalendarDayState is KalendarDayState.KalendarDaySelected) {
    Constant.selectedDotColor
} else {
    Color.Transparent
}

private fun getTextColor(
    kalendarDayState: KalendarDayState,
    kalendarDayColors: KalendarDayColors,
): Color = if (kalendarDayState is KalendarDayState.KalendarDaySelected) {
    kalendarDayColors.selectedTextColor
} else {
    kalendarDayColors.textColor
}

//@Preview
//@Composable
//private fun KalendarDayPreview() {
//    KalendarDay()
//}
