package com.sugarspoon.otpview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@Composable
fun <T> RadioGroup(
    modifier: Modifier,
    options: List<T>,
    selected: T,
    onSelected: (T) -> Unit,
    content: @Composable RowScope.(T) -> Unit
) {
    Column(
        modifier = modifier.background(MaterialTheme.colors.background),
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics {
                        role = Role.RadioButton
                        this.selected = selected == option
                    }
                    .clickable {
                        onSelected(option)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    modifier = Modifier.clearAndSetSemantics {  },
                    selected = selected == option,
                    onClick = {
                        onSelected(option)
                    }
                )
                content(option)
            }
        }
    }
}