package com.example.customcalendar.customCalenderClasses.ui.component.header

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customcalendar.customCalenderClasses.ui.component.button.CustomCalenderIconButton
import com.example.customcalendar.customCalenderClasses.ui.component.text.CustomCalenderSubTitle
import com.example.customcalendar.customCalenderClasses.utils.Constant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.time.Month
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomCalenderHeader(
    month: Month,
    year: Int,
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onTodayClick: () -> Unit = {},
    arrowShown: Boolean = true,
    customCalenderHeaderConfig: CustomCalenderHeaderConfig,
    selectedDate: LocalDate?
    ) {
    val isNext = remember { mutableStateOf(true) }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .fillMaxWidth()
            .border(BorderStroke(2.dp, Color.Gray))
            .wrapContentHeight()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (arrowShown) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ArrowIconsAndMonthUi(
                    isNext,
                    onPreviousClick,
                    onNextClick,
                    month,
                    year,
                    customCalenderHeaderConfig
                )

                CloseIconAndTodayBadgeUi(modifier, customCalenderHeaderConfig, selectedDate,onTodayClick)

            }
        }
    }
}

@Composable
private fun CloseIconAndTodayBadgeUi(
    modifier: Modifier,
    customCalenderHeaderConfig: CustomCalenderHeaderConfig,
    selectedDate: LocalDate?,
    onTodayClick : () -> Unit = {},
) {
    Row (
        modifier = Modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
            ){
        AnimatedVisibility(visible = !selectedDate.toString().equals(Clock.System.todayIn(TimeZone.currentSystemDefault()).toString())) {

            Card(
                modifier = modifier
                    .wrapContentSize()
                    .clickable {
                        onTodayClick()
                               },
                backgroundColor = Constant.selectedDotColor,
                shape = RoundedCornerShape(3.dp),
            ) {
                Text(
                    text = "Today",
                    color = Color.White,
//                fontSize = customCalenderHeaderConfig.customCalenderTextConfig.customCalenderTextSize.size,
                    fontSize = 18.sp,
                    modifier = modifier.padding(vertical = 2.dp, horizontal = 6.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }


        CustomCalenderIconButton(
            modifier = Modifier.wrapContentSize(),
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            onClick = {

            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun ArrowIconsAndMonthUi(
    isNext: MutableState<Boolean>,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    month: Month,
    year: Int,
    customCalenderHeaderConfig: CustomCalenderHeaderConfig
) {

    Row(
        modifier = Modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically

    ) {
        CustomCalenderIconButton(
            modifier = Modifier
                .wrapContentSize() ,
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Previous Week",
            onClick = {
                isNext.value = false
                onPreviousClick()
            }

        )
        Spacer(modifier = Modifier.width(5.dp))

        CustomCalenderIconButton(
            modifier = Modifier.wrapContentSize(),
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next Month",
            onClick = {
                isNext.value = true
                onNextClick()
            }
        )

        Spacer(modifier = Modifier.width(10.dp))

        AnimatedContent(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .align(Alignment.CenterVertically),
            targetState = getTitleText(month, year),
            transitionSpec = {
                addAnimation(isNext = isNext.value).using(
                    SizeTransform(clip = false)
                )
            }
        )
        {
            CustomCalenderSubTitle(
                text = it,
                modifier = Modifier,
                customCalenderTextConfig = customCalenderHeaderConfig.customCalenderTextConfig
            )
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
internal fun addAnimation(duration: Int = 500, isNext: Boolean): ContentTransform {
    return slideInHorizontally(animationSpec = tween(durationMillis = duration)) { height -> if (isNext) height else -height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    ) with slideOutHorizontally(animationSpec = tween(durationMillis = duration)) { height -> if (isNext) -height else height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    )

}

@RequiresApi(Build.VERSION_CODES.O)
internal fun getTitleText(month: Month, year: Int): String {
    return month.getDisplayName(TextStyle.FULL, Locale.getDefault()).lowercase().replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.getDefault()
        ) else it.toString()
    } + " " + year
}
