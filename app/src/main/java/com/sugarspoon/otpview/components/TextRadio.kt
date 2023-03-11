package com.sugarspoon.otpview.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.sugarspoon.otpview.theme.Typography

@Composable
fun TextRadio(
    text: String
) {
    Text(
        text = text,
        style = Typography.body2
    )
}