package com.example.customcalendar.kalendarClasses.ui.component.day

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
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
    size: Dp = 45.dp,
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

    val constraints= ConstraintSet {
        val KalendarNormalText = createRefFor("KalendarNormalText")
        val Row = createRefFor("Row")

        constrain(KalendarNormalText){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)

            width= Dimension.wrapContent
            height = Dimension.wrapContent
        }
        constrain(Row){
            top.linkTo(KalendarNormalText.bottom)
            width= Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }


    Box(
        modifier = modifier
            .border(border = border, shape = CircleShape)
            .clip(shape = CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
//                val kalendarEventForDay = kalendarEvents.filter { it.date == kalendarDay.localDate }
//                if (kalendarEventForDay.isEmpty()){
//                }else {
                    onCurrentDayClick(kalendarDay, kalendarEvents)
//                }
            }
            .size(size)
            .background(color = bgColor),
        contentAlignment = Alignment.Center
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxSize()
        ){
            KalendarNormalText(
            text = kalendarDay.localDate.dayOfMonth.toString(),
            modifier = Modifier.layoutId("KalendarNormalText"),
            fontWeight = weight,
            textColor = textColor,
            textSize = textSize,
        )
            Row(
                modifier = Modifier
                    .layoutId("Row")
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val kalendarEventForDay = kalendarEvents.filter { it.date == kalendarDay.localDate }
                if (kalendarEventForDay.isNotEmpty()) {
                    val dayEvents = kalendarEventForDay
                    dayEvents.forEach {
                        KalendarDots(
                            modifier = Modifier
                        )
                    }
                }
            }

        }

    }
}

@Composable
fun KalendarDots(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                color = Color.Red
            )
            .size(5.dp)
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
