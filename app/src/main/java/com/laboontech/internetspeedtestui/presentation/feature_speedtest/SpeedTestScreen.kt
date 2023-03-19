package com.laboontech.internetspeedtestui.presentation.feature_speedtest


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.laboontech.internetspeedtestui.R
import com.laboontech.internetspeedtestui.presentation.ui.theme.ArcColorPrimary
import com.laboontech.internetspeedtestui.presentation.ui.theme.DarkGradient
import com.laboontech.internetspeedtestui.presentation.ui.theme.LightColor
import com.laboontech.internetspeedtestui.presentation.ui.theme.ArcGradient
import com.laboontech.internetspeedtestui.presentation.ui.theme.DarkColor
import com.laboontech.internetspeedtestui.presentation.ui.theme.DarkColor2
import com.laboontech.internetspeedtestui.presentation.ui.theme.LightColor2
import com.laboontech.internetspeedtestui.presentation.ui.theme.TitleColor
import com.laboontech.internetspeedtestui.presentation.uistate.UiState
import com.laboontech.internetspeedtestui.utils.BottomMenuContent
import com.laboontech.internetspeedtestui.utils.Constants
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt


/**
 * SpeedTest Main Screen
 * */
@Composable
fun SpeedTestScreen() {


    val coroutineScope = rememberCoroutineScope()

    val animation = remember {
        Animatable(0f)
    }

    val maxSpeed = remember { mutableStateOf(0f) }
    maxSpeed.value = java.lang.Float.max(maxSpeed.value, animation.value * 100f)



    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkGradient)
        ) {
            Header()
            Spacer(modifier = Modifier.height(30.dp))
            SpeedIndicatorContent(state = animation.toUiState(maxSpeed.value)) {
                coroutineScope.launch {
                    maxSpeed.value = 0f
                    startAnimation(animation)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            SpeedInformation(state = animation.toUiState(maxSpeed.value))
        }

        BottomMenu(
            items = listOf(
                BottomMenuContent(
                    stringResource(id = R.string.network),
                    R.drawable.baseline_signal_wifi_statusbar_connected_no_internet_4_24
                ),
                BottomMenuContent(
                    stringResource(id = R.string.speed),
                    R.drawable.baseline_speed_24
                ),
                BottomMenuContent(
                    stringResource(id = R.string.location),
                    R.drawable.baseline_person_pin_circle_24
                )
            ),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }


}


suspend fun startAnimation(animation: Animatable<Float, AnimationVector1D>) {
    animation.animateTo(0.65f, keyframes {
        durationMillis = 9000
        0f at 0 with CubicBezierEasing(0f, 1.5f, 0.8f, 1f)
        0.12f at 1000 with CubicBezierEasing(0.2f, -1.5f, 0f, 1f)
        0.20f at 2000 with CubicBezierEasing(0.2f, -2f, 0f, 1f)
        0.35f at 3000 with CubicBezierEasing(0.2f, -1.5f, 0f, 1f)
        0.62f at 4000 with CubicBezierEasing(0.2f, -2f, 0f, 1f)
        0.75f at 5000 with CubicBezierEasing(0.2f, -2f, 0f, 1f)
        0.89f at 6000 with CubicBezierEasing(0.2f, -1.2f, 0f, 1f)
        0.82f at 7500 with LinearOutSlowInEasing
    })
}


fun Animatable<Float, AnimationVector1D>.toUiState(maxSpeed: Float) = UiState(
    arcValue = value,
    speed = "%.1f".format(value * 100),
    uploadSpeed = "%.1f".format(value * 50),
    ping = if (value > 0.2f) "${(value * 15).roundToInt()} ms" else "0.0 ms",
    maxSpeed = if (maxSpeed > 0f) "%.1f mbps".format(maxSpeed) else "0.0 mbps",
    inProgress = isRunning
)

/**
 * Header View - Header Title and Setting Icon
 * */
@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif
        )
        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = stringResource(id = R.string.settings)
        )

    }
}


/**
 * Download Speed, Upload Speed, Max Speed and Ping Information
 * */
@Composable
fun SpeedInformation(
    state: UiState
) {

    @Composable
    fun RowScope.InfoColumn(title: String, value: String) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = TitleColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    // Download Speed
    // Upload Speed
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        InfoColumn(
            title = stringResource(id = R.string.download_speed),
            value = "${state.speed} mbps"
        )
        VerticalDivider()
        InfoColumn(
            title = stringResource(id = R.string.upload_speed),
            value = "${state.uploadSpeed} mbps"
        )
    }

    Spacer(modifier = Modifier.height(40.dp))

    // Ping
    // Max Speed
    Row(
        Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        InfoColumn(title = stringResource(id = R.string.ping), value = state.ping)
        VerticalDivider()
        InfoColumn(
            title = stringResource(id = R.string.max_speed),
            value = state.maxSpeed
        )
    }
}


/**
 * Vertical Divider
 * */
@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFF414D66))
            .width(1.dp)
    )
}

/**
 * This function contains - Speed Indicator, Speed Value text, Start/Stop button
 * */
@Composable
fun SpeedIndicatorContent(
    state: UiState,
    onclick: () -> Unit
) {

    val buttonHorizontalPadding by animateDpAsState(
        targetValue = if (state.inProgress) 40.dp else 20.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {


        /* Circular speed indicator */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {

            // Draw Scale lines
            drawLines(state.arcValue, Constants.MAX_VALUE_FOR_LINES)

            // Draw Arc
            drawArcs(state.arcValue, Constants.MAX_VALUE_FOR_LINES)
        }


        /* Speed value Texts */
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Download Text
            Text(
                text = stringResource(id = R.string.download),
                style = MaterialTheme.typography.caption,
                color = Color.White
            )


            // Speed Value
            Text(
                text = state.speed,
                fontSize = 45.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )

            // mbps text
            Text(
                text = stringResource(id = R.string.mbps),
                style = MaterialTheme.typography.caption,
                color = Color.White
            )


        }





        /* Start check speed value Button */
        OutlinedButton(
            onClick = {
                if (!state.inProgress) onclick()
            },
            modifier = Modifier.padding(bottom = 24.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(width = 1.dp, color = LightColor2),
        ) {

            // Start button text
            Text(
                modifier = Modifier
                    .padding(horizontal = buttonHorizontalPadding, vertical = 4.dp),
                text = if (!state.inProgress) {
                    stringResource(id = R.string.start)
                } else {
                    stringResource(id = R.string.checking_internet_speed)
                },
                color = if (state.inProgress) {
                    Color.White.copy(alpha = 0.5f)
                } else {
                    Color.White
                }

            )

        }
    }


}


/**
 * Draw arcs extension function to draw an speed indicator arc above the lines
 * */
fun DrawScope.drawArcs(progress: Float, maxValue: Float) {
    val startAngle = 270 - maxValue / 2
    val sweepAngle = maxValue * progress

    val topLeft = Offset(50f, 50f)
    val size = Size(size.width - 100f, size.height - 100f)

    fun drawBlur() {
        for (i in 0..20) {
            drawArc(
                color = ArcColorPrimary.copy(alpha = i / 900f),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = 80f + (20 - i) * 20, cap = StrokeCap.Round)
            )
        }
    }

    fun drawStroke() {
        drawArc(
            color = ArcColorPrimary,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 86f, cap = StrokeCap.Round)
        )
    }

    fun drawGradient() {
        drawArc(
            brush = ArcGradient,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = topLeft,
            size = size,
            style = Stroke(width = 80f, cap = StrokeCap.Round)
        )
    }

    drawBlur()
    drawStroke()
    drawGradient()
}

/**
 * Draw lines extension function to draw lines which look like a round scale
 * */
fun DrawScope.drawLines(
    progress: Float,
    maxValue: Float,
    numberOfLines: Int = Constants.DEFAULT_NUMBER_OF_LINES
) {

    // To calculate rotation size
    val oneRotation = maxValue / numberOfLines
    // Start value of indicator, it can start from 0 or from current progress
    val startValue = if (progress == 0f) 0 else floor(x = progress * numberOfLines).toInt() + 1


    // Loop starts from start value to the number of lines
    for (i in startValue..numberOfLines) {
        val rotationDegree = i * oneRotation + (180 - maxValue) / 2
        rotate(degrees = rotationDegree) {
            drawLine(
                color = LightColor,
                start = Offset(if (i % 5 == 0) 80f else 30f, size.height / 2),
                end = Offset(0f, size.height / 2),
                strokeWidth = 8f,
                cap = StrokeCap.Round
            )
        }
    }


}


@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = LightColor,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = LightColor2,
    initialSelectedItemIndex: Int = 1
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DarkColor)
            .padding(15.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = LightColor,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = LightColor2,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
                onItemClick()
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if (isSelected) activeTextColor else inactiveTextColor
        )
    }

}

@Preview(device = Devices.PIXEL)
@Composable
fun SpeedTestScreenPreview() {
    SpeedTestScreen()
}