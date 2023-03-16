package com.sugarspoon.otpview.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = AquaMarine,
    primaryVariant = BabyBlue,
    secondary = AquaMarine,
    error = FeedbackError
)

private val LightColorPalette = lightColors(
    primary = AquaMarine,
    primaryVariant = BabyBlue,
    secondary = AquaMarine,
    error = FeedbackError
)

@Composable
fun OtpViewTheme(isLightTheme: Boolean = true, content: @Composable () -> Unit) {
    val colors = if (isLightTheme) {
        LightColorPalette
    } else {
        DarkColorPalette
    }
    val systemUiController = rememberSystemUiController()
    if(isLightTheme) {
        systemUiController.setStatusBarColor(
            color = LightStatusBar
        )
    } else {
        systemUiController.setStatusBarColor(
            color = DarkStatusBar
        )
    }
    systemUiController.isNavigationBarVisible = false

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}