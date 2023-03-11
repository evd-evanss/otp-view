package com.sugarspoon.otpview.ui.sample

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sugarspoon.otpview.OtpSize
import com.sugarspoon.otpview.OtpView
import com.sugarspoon.otpview.OtpViewStyles
import com.sugarspoon.otpview.R
import com.sugarspoon.otpview.components.GenericDialog
import com.sugarspoon.otpview.components.HeadingLevel2
import com.sugarspoon.otpview.components.RadioGroup
import com.sugarspoon.otpview.components.TextRadio
import com.sugarspoon.otpview.components.rememberDialogState
import com.sugarspoon.otpview.theme.OtpCircleShape
import com.sugarspoon.otpview.theme.OtpRoundedCornerShape
import com.sugarspoon.otpview.theme.Typography

@Composable
fun SampleScreen(
    viewModel: SampleViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val dialogState = rememberDialogState()
    dialogState.handleState(state.displayDialog)
    GenericDialog(
        state = dialogState,
        onDismissListener = viewModel::closeKeyboardDialog
    ) {

        CardContent(
            shape = state.shape,
            modifier = Modifier.padding(horizontal = HorizontalSpacing)
        ) {
            HeadingLevel2(
                text = "KeyboardTypes",
                modifier = Modifier.padding(
                    vertical = VerticalSpacing,
                    horizontal = HorizontalSpacing
                )
            )
            RadioGroup(
                modifier = Modifier.padding(
                    bottom = VerticalSpacing,
                    start = VerticalSpacing,
                    end = VerticalSpacing
                ),
                options = state.keyboardsOption,
                selected = state.keyboardSelected,
                onSelected = viewModel::selectKeyboard
            ) {
                TextRadio(text = it)
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OtpView(
            modifier = Modifier
                .padding(OtpSpacing)
                .imePadding(),
            colors = OtpColors(),
            onAllDigitsFilled = viewModel::filledPassword,
            isError = state.isError,
            isEnabled = state.isEnabled,
            isSecret = state.isSecret,
            onPressedDelKey = viewModel::pressedDelKey,
            styles = OtpStyle.copy(shape = state.shape),
            keyboardType = state.keyboardType
        )
        CardContent(
            shape = state.shape,
            modifier = Modifier.padding(horizontal = HorizontalSpacing)
        ) {
            HeadingLevel2(
                text = stringResource(id = R.string.shape_header),
                modifier = Modifier.padding(
                    vertical = VerticalSpacing / 2,
                    horizontal = HorizontalSpacing
                )
            )
            RadioGroup(
                modifier = Modifier,
                options = state.shapesOptions,
                selected = state.shapeSelected,
                onSelected = viewModel::selectShape
            ) {
                TextRadio(text = it)
            }
        }
        CardContent(
            shape = state.shape,
            modifier = Modifier.padding(horizontal = HorizontalSpacing, vertical = VerticalSpacing)
        ) {
            HeadingLevel2(
                text = stringResource(id = R.string.states_header),
                modifier = Modifier.padding(
                    vertical = VerticalSpacing / 2,
                    horizontal = HorizontalSpacing
                )
            )
            RadioGroup(
                modifier = Modifier,
                options = state.statesOption,
                selected = state.stateSelected,
                onSelected = viewModel::selectState
            ) {
                TextRadio(text = it)
            }
        }
        Box(modifier = Modifier.weight(1f)
            .imePadding()) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = HorizontalSpacing,
                        end = HorizontalSpacing,
                        bottom = VerticalSpacing * 2
                    ),
                shape = state.shape,
                onClick = viewModel::openKeyboardDialog
            ) {
                Text(text = "Change KeyboardType")
            }
        }
    }
}

@Composable
fun CardContent(
    shape: Shape,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        shape = if(shape == OtpCircleShape) OtpRoundedCornerShape else shape,
        modifier = modifier,
        content = {
            Column { content() }
        },
        elevation = 4.dp
    )
}

private val OtpSpacing = 8.dp
private val VerticalSpacing = 8.dp
private val HorizontalSpacing = 16.dp
private val OtpStyle = OtpViewStyles.default(otpSize = OtpSize.FOUR, textStyle = Typography.h1)
private val OtpColors: @Composable () -> TextFieldColors = {
    TextFieldDefaults.textFieldColors(
        backgroundColor = MaterialTheme.colors.background,
        errorLabelColor = MaterialTheme.colors.error,
        errorTrailingIconColor = MaterialTheme.colors.error,
        errorCursorColor = MaterialTheme.colors.error,
        errorIndicatorColor = MaterialTheme.colors.error,
        errorLeadingIconColor = MaterialTheme.colors.error
    )
}