package com.sugarspoon.otpview.ui.sample.events

import com.sugarspoon.otpview.base.ScreenEvent

sealed class SampleEvent : ScreenEvent {
    data class OnSelectShape(val option: String): SampleEvent()
    data class OnSelectState(val option: String): SampleEvent()
    data class OnSelectKeyboard(val option: String): SampleEvent()
    data class OnPasswordFilled(val password: String): SampleEvent()
    object OnOpenKeyboardDialog: SampleEvent()
    object OnCloseKeyboardDialog: SampleEvent()
    object OnPressedDelKey: SampleEvent()
}
