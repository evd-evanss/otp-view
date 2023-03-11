package com.sugarspoon.otpview.ui.sample

import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.KeyboardType
import com.sugarspoon.otpview.base.BaseViewModel
import com.sugarspoon.otpview.theme.OtpCircleShape
import com.sugarspoon.otpview.theme.OtpRoundedCornerShape
import com.sugarspoon.otpview.ui.sample.events.SampleEvent
import com.sugarspoon.otpview.ui.sample.state.KeyboardsOption
import com.sugarspoon.otpview.ui.sample.state.ShapesOptions
import com.sugarspoon.otpview.ui.sample.state.SampleState
import com.sugarspoon.otpview.ui.sample.state.StatesOption
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SampleViewModel: BaseViewModel<SampleState, SampleEvent>(SampleState()) {

    override fun reduce(
        oldState: SampleState,
        sideEffect: SampleEvent
    ) {
        when(sideEffect) {
            is SampleEvent.OnSelectShape -> {
                createNewState(
                    newState = oldState.copy(
                        shapeSelected = sideEffect.option,
                        shape = when(sideEffect.option) {
                            ShapesOptions.Rect.option -> RectangleShape
                            ShapesOptions.Circle.option -> OtpCircleShape
                            else -> OtpRoundedCornerShape
                        }
                    )
                )
            }
            is SampleEvent.OnSelectState -> {
                createNewState(
                    newState = oldState.copy(
                        stateSelected = sideEffect.option,
                        isError = sideEffect.option == StatesOption.Error.option,
                        isEnabled = sideEffect.option != StatesOption.Disabled.option,
                        isSecret = sideEffect.option == StatesOption.Secret.option
                    )
                )
            }
            is SampleEvent.OnSelectKeyboard -> {
                createNewState(
                    newState = oldState.copy(
                        keyboardSelected = sideEffect.option,
                        keyboardType = when(sideEffect.option) {
                            KeyboardsOption.Number.option -> KeyboardType.Number
                            else -> KeyboardType.Text
                        },
                        displayDialog = false
                    )
                )
            }
            is SampleEvent.OnPasswordFilled -> {
                createNewState(
                    newState = oldState.copy(
                        password = sideEffect.password
                    )
                )
            }
            is SampleEvent.OnPressedDelKey -> {
                createNewState(
                    newState = oldState.copy(isError = false)
                )
            }
            is SampleEvent.OnOpenKeyboardDialog -> {
                createNewState(
                    newState = oldState.copy(displayDialog = true)
                )
            }
            is SampleEvent.OnCloseKeyboardDialog -> {
                createNewState(
                    newState = oldState.copy(displayDialog = false)
                )
            }
        }
    }

    fun filledPassword(password: String) {
        emitEvent(SampleEvent.OnPasswordFilled(password))
    }

    fun pressedDelKey() {
        emitEvent(SampleEvent.OnPressedDelKey)
    }

    fun selectShape(option: String) {
        emitEvent(SampleEvent.OnSelectShape(option))
    }

    fun selectState(option: String) {
        emitEvent(SampleEvent.OnSelectState(option))
    }

    fun openKeyboardDialog() {
        emitEvent(SampleEvent.OnOpenKeyboardDialog)
    }

    fun closeKeyboardDialog() {
        emitEvent(SampleEvent.OnCloseKeyboardDialog)
    }

    fun selectKeyboard(option: String) {
        emitEvent(SampleEvent.OnSelectKeyboard(option))
    }
}



