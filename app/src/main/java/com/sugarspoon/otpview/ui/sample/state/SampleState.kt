package com.sugarspoon.otpview.ui.sample.state

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sugarspoon.otpview.base.ScreenState

data class SampleState(
    val shape: Shape = RoundedCornerShape(8.dp),
    val shapesOptions: List<String> = ShapesOptions.getOptions(),
    val statesOption: List<String> = StatesOption.getOptions(),
    val keyboardsOption: List<String> = KeyboardsOption.getOptions(),
    val keyboardType: KeyboardType = KeyboardType.Number,
    val password: String = "",
    val displayDialog: Boolean = false,
    val isError: Boolean = false,
    val isEnabled: Boolean = true,
    val isSecret: Boolean = false,
    val shapeSelected: String = ShapesOptions.getOptions().first(),
    val stateSelected: String = StatesOption.getOptions().first(),
    val keyboardSelected: String = KeyboardsOption.getOptions().first(),
) : ScreenState

enum class ShapesOptions(val option: String) {
    RoundedCorner("Rounded corners"),
    Rect("Rectangular"),
    Circle("Circle");

    companion object {
        fun getOptions() = listOf(
            RoundedCorner.option,
            Rect.option,
            Circle.option
        )
    }
}

enum class StatesOption(val option: String) {
    Enabled("Enabled"),
    Disabled("Disabled"),
    Secret("Secret"),
    Error("Error");

    companion object {
        fun getOptions() = listOf(
            Enabled.option,
            Disabled.option,
            Secret.option,
            Error.option
        )
    }
}

enum class KeyboardsOption(val option: String) {
    Number("Number"),
    Text("Text");

    companion object {
        fun getOptions() = listOf(
            Number.option,
            Text.option
        )
    }
}