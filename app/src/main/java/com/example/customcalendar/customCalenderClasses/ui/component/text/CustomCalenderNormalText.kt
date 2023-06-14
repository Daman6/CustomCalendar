package com.example.customcalendar.customCalenderClasses.ui.component.text

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomCalenderNormalText(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight,
    textColor: Color,
    textSize: TextUnit = CustomCalenderTextSize.Normal.size

) {

    Text(
        modifier = modifier.wrapContentSize(),
        color = textColor,
        fontSize = textSize,
        text = text,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center
    )

}

@Composable
fun CustomCalenderTextDisabled(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight,
    textColor: Color,
    textSize: TextUnit = CustomCalenderTextSize.Normal.size
) {

    Text(
        modifier = modifier.wrapContentSize()
            .clickable(enabled = false){},
        color = textColor,
        fontSize = textSize,
        text = text,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center
    )
}

@Preview
@Composable
private fun CustomCalenderNormalTextPreview() {

    CustomCalenderNormalText(
        text = "1",
        modifier = Modifier,
        fontWeight = FontWeight.SemiBold,
        textColor = Color.Black,
        textSize = 26.sp
    )

}
