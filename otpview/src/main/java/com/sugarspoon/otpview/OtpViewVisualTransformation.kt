package com.sugarspoon.otpview

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class OtpViewVisualTransformation(val mask: Char = '\u2B24') : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            AnnotatedString(mask.toString().repeat(text.text.length)),
            OffsetMapping.Identity,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PasswordVisualTransformation) return false
        if (mask != other.mask) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}
