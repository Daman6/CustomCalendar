package com.example.customcalendar.customCalenderClasses.ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.customcalendar.customCalenderClasses.ui.component.text.config.KalendarTextConfig
import com.example.customcalendar.customCalenderClasses.ui.component.text.config.KalendarTextDefaults


@Composable
fun KalendarSubTitle(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.SemiBold,
    kalendarTextConfig: KalendarTextConfig = KalendarTextDefaults.kalendarSubTitleTextConfig()
) {
    Text(
        modifier = modifier,
        color = Color.White,
        fontSize = kalendarTextConfig.kalendarTextSize.size,
        text = text,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}

@Preview
@Composable
private fun KalendarSubTitlePreview() {
    KalendarSubTitle(modifier = Modifier, text = "Hye Himanshu")
}
