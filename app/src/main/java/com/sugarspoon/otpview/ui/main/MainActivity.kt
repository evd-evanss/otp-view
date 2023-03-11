package com.sugarspoon.otpview.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sugarspoon.otpview.components.HeadingLevel1
import com.sugarspoon.otpview.ui.sample.SampleScreen
import com.sugarspoon.otpview.theme.OtpViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var appTheme by rememberSaveable { mutableStateOf<Boolean?>(null) }
            val appThemeFinal = appTheme ?: !isSystemInDarkTheme()

            OtpViewTheme(appThemeFinal) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                HeadingLevel1(
                                    text = "Sample Otp View",
                                    modifier = Modifier
                                )
                            },
                            actions = {
                                IconButton(
                                    onClick = {
                                        appTheme = !appThemeFinal
                                    }
                                ) {
                                    Icon(Icons.Default.BrightnessMedium, contentDescription = null)
                                }
                            }
                        )
                    },
                ) { paddingValues ->
                    paddingValues.apply { SampleScreen() }
                }
            }
        }
    }
}