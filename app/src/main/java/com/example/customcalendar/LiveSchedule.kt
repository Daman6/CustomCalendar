package com.example.customcalendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.schedule.model.GameDetails
import com.example.schedule.model.TeamDetails
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GamesLists() {


    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(colorResource(id = R.color.header2_background))
    ) {
        var btnState by remember {
            mutableStateOf(false)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Schedule",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        btnState = !btnState
                    },
                painter = painterResource(id = if (btnState) R.drawable.outline_close_24 else R.drawable.outline_calendar_month_24),
                contentDescription = null,
                tint = Color.White,
            )
        }
        AnimatedVisibility(visible = btnState) {
            KalendarDemo()
        }
        LazyRow(
            Modifier
                .padding(top = 10.dp, bottom = 10.dp, start = 10.dp)
        ) {
            items(getGameList()) {
                GameStats(it)
            }
        }
    }
}

fun getGameList(): kotlin.collections.List<GameDetails> {
    return listOf<GameDetails>(
        GameDetails(
            "ENDED",
            TeamDetails(R.drawable.team1_logo, "CAPITAL", 4, R.drawable.team2_logo, "DEVILS", 2)
        ),
        GameDetails(
            "STARTED",
            TeamDetails(R.drawable.team1_logo, "CAPITAL", 4, R.drawable.team2_logo, "DEVILS", 6)
        ),
        GameDetails(
            "RUNNING",
            TeamDetails(R.drawable.team1_logo, "CAPITAL", 4, R.drawable.team2_logo, "DEVILS", 4)
        ),
    )
}

@Composable
fun GameStats(gameDetails: GameDetails) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp - 80.dp
    Log.e("ndn", screenWidth.value.toString())
    Column(
        modifier = Modifier
            .width(screenWidth)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(colorResource(id = R.color.item_background)),
    ) {
        GameStatsHeader(gameDetails)
        Spacer(modifier = Modifier.height(5.dp))
        TeamDetailLive(gameDetails, checkHighestScoreTeam(gameDetails.teamDetails))
        Spacer(modifier = Modifier.height(5.dp))
        BottomButtonLayout(gameDetails)
        Spacer(modifier = Modifier.height(8.dp))

    }
}

@Composable
fun GameStatsHeader(gameDetails: GameDetails) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    if (gameDetails.gameType.equals("ENDED")) {
        FinalGameHeaderUi()
    } else if (gameDetails.gameType.equals("STARTED")) {
        StaretedGameHeaderUi()
    } else {
        PostGameHeaderUi()
    }
}

@Composable
fun FinalGameHeaderUi() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.Blue
    ) {
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "FINAL",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun StaretedGameHeaderUi() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.Red
    ) {
        Row(
            modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "12/22",
                    color = Color.White,
                    fontSize = 14.sp

                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Live",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 14.sp

                )
            }
            Text(
                text = "ABC/ESPN+",
                color = Color.White,
                fontSize = 14.sp

            )
        }
    }
}

@Composable
fun PostGameHeaderUi() {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = colorResource(id = R.color.header2_background)
    ) {
        Row(
            modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = "12/22", color = Color.White,
                    fontSize = 14.sp

                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "@ 8PM",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    fontSize = 14.sp

                )
            }
            Text(
                text = "ABC/ESPN+",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BottomButtonLayout(gameDetails: GameDetails) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp


    if (gameDetails.gameType.equals("ENDED")) {
        SingleBtn()
    } else {
        TwoBtn()
    }
}

@Composable
fun SingleBtn() {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            border = BorderStroke(2.dp, colorResource(id = R.color.header2_background)),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.item_background))
        ) {
            Text(
                text = "GO TO GAME CENTER",
                fontSize = 10.sp,
                color = Color.White
            )

        }
    }
}

@Composable
fun TwoBtn() {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.item_background)),
            border = BorderStroke(1.dp, colorResource(id = R.color.border_background)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) {
            Text(
                text = "Buy Tickets",
                color = Color.White,
                fontSize = 10.sp,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.item_background)),
            border = BorderStroke(1.dp, colorResource(id = R.color.border_background)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "Set Alert",
                color = Color.White,
                fontSize = 10.sp,
            )
        }

    }
}


@Composable
fun TeamDetailLive(gameDetails: GameDetails, result: String) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    Column() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(id = gameDetails.teamDetails.teamLogo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = gameDetails.teamDetails.teamName.uppercase(Locale.getDefault()),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color =
                    if (gameDetails.gameType.equals("ENDED")) {
                        if (result.equals("TEAM1")) Color.White else Color.LightGray
                    } else {
                        Color.White
                    }
                )
            }
            Text(
                text = gameDetails.teamDetails.teamScore.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color =
                if (gameDetails.gameType.equals("ENDED")) {
                    if (result.equals("TEAM1")) Color.White else Color.LightGray
                } else {
                    Color.White
                }
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(id = gameDetails.teamDetails.team2Logo),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = gameDetails.teamDetails.team2Name.uppercase(Locale.getDefault()),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color =
                    if (gameDetails.gameType.equals("ENDED")) {
                        if (result.equals("TEAM2")) Color.White else Color.LightGray
                    } else {
                        Color.White
                    }
                )
            }
            Text(
                text = gameDetails.teamDetails.team2Score.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color =
                if (gameDetails.gameType.equals("ENDED")) {
                    if (result.equals("TEAM2")) Color.White else Color.LightGray
                } else {
                    Color.White
                }
            )
        }
    }
}

fun checkHighestScoreTeam(teamDetails: TeamDetails): String {
    if (teamDetails.teamScore > teamDetails.team2Score) {
        return "TEAM1"
    } else if (teamDetails.teamScore < teamDetails.team2Score) {
        return "TEAM2"
    } else {
        return "TIE"
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun GameStatePreview() {
    GamesLists()
}