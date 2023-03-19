package com.laboontech.internetspeedtestui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.laboontech.internetspeedtestui.presentation.feature_speedtest.SpeedTestScreen
import com.laboontech.internetspeedtestui.presentation.ui.theme.InternetSpeedTesterTheme



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InternetSpeedTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SpeedTestScreen()
                }
            }
        }
    }


}

