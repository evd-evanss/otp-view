package com.sugarspoon.otpview.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.sugarspoon.otpview.theme.Typography

@Composable
fun HeadingLevel2(
    text: String,
    modifier: Modifier
) {
    Text(
        text = text,
        style = Typography.h2,
        modifier = modifier.semantics { heading() }
    )
}