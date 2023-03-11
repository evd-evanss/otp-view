package com.sugarspoon.otpview.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog

@Composable
fun GenericDialog(
    state: DialogState,
    onDismissListener: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    if (state.isOpened) {
        Dialog(
            onDismissRequest = onDismissListener
        ) {
            Column { content() }
        }
    }
}

@Composable
fun rememberDialogState() = DialogState()

class DialogState {

    var isOpened by mutableStateOf(false)
        private set

    fun handleState(display: Boolean) {
        isOpened = display
    }
}