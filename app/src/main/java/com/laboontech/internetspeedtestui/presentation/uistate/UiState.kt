package com.laboontech.internetspeedtestui.presentation.uistate

class UiState(
    val speed: String = "",
    val uploadSpeed: String = "",
    val ping: String = "-",
    val maxSpeed: String = "-",
    val arcValue: Float = 0f,
    val inProgress: Boolean = false
)