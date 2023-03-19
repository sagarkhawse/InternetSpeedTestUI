package com.laboontech.internetspeedtestui.presentation.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF7553B1)
val Purple700 = Color(0xFF673AB7)
val Teal200 = Color(0xFF03DAC5)

val DarkColor = Color(0xFF020B29)
val DarkColor2 = Color(0xFF112055)
val LightColor = Color(0xff505cf3)
val LightColor2 = Color(0xFF7A82E9)

val ArcColorPrimary = Color(0xFFFFC107)
val ArcColorSecondary = Color(0xFFFFE500)

val TitleColor = Color(0xFFBCC5BC)


val ArcGradient = Brush.linearGradient(
    colors = listOf(ArcColorSecondary, ArcColorPrimary),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val DarkGradient = Brush.verticalGradient(
    colors = listOf(DarkColor2, DarkColor)
)