package com.sugarspoon.otpview.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.toFontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.sugarspoon.otpview.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Font(R.font.poppins_medium).toFontFamily(),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        )
    ),
    body2 = TextStyle(
        fontFamily = Font(R.font.poppins_medium).toFontFamily(),
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        )
    ),
    h1 = TextStyle(
        fontFamily = Font(R.font.poppins_bold).toFontFamily(),
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        )
    ),
    h2 = TextStyle(
        fontFamily = Font(R.font.poppins_bold).toFontFamily(),
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        )
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)