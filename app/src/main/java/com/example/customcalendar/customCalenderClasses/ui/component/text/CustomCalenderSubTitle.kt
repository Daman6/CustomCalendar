package com.example.customcalendar.customCalenderClasses.ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun CustomCalenderSubTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.SemiBold,
    customCalenderTextConfig: CustomCalenderTextConfig = CustomCalenderTextDefaults.customCalenderSubTitleTextConfig()
) {
    Text(
        modifier = modifier,
        color = Color.White,
//        fontSize = customCalenderTextConfig.customCalenderTextSize.size,
        fontSize = 18.sp,
        text = text,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Preview
@Composable
private fun CustomCalenderSubTitlePreview() {
    CustomCalenderSubTitle(modifier = Modifier, text = "Hye Himanshu")
}
