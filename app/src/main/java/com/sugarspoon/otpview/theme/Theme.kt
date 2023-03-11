package com.sugarspoon.otpview.theme

import androidx.compose.foundation.isSystemInDarkTheme
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
fun OtpViewTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val systemUiController = rememberSystemUiController()
    if(darkTheme) {
        systemUiController.setStatusBarColor(
            color = DarkStatusBar
        )
    } else {
        systemUiController.setStatusBarColor(
            color = LightStatusBar
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