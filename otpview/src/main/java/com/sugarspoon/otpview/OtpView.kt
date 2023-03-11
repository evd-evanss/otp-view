package com.sugarspoon.otpview

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.KeyEvent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * OtpView
 * @author Evandro Costa - https://github.com/evd-evanss
 *
 * @param modifier An ordered, immutable collection of modifier elements that decorate or add
 * behavior to Compose UI elements. For example, backgrounds, padding and click event listeners
 * decorate or add behavior to rows, text or buttons.
 *
 * @param colors Represents the colors of the input text, background and content used in a
 * text field in different states.
 *
 * @param styles Defines text style, shape and number of text fields.
 *
 * @param isSecret Defines whether to show or hide the entered number. Default [false]
 *
 * @param isError Indicates if the text field's current value is in error state. If set to true,
 * the label, shape and border will be displayed in error color. Default [false]
 *
 * @param isEnabled Controls the enabled state of the [OtpView]. When false, the text field
 * will be neither editable nor focusable, the input of the text field will not be selectable.
 *
 * @param keyboardType The keyboard type to be used in this text field. Note that this input type is
 * honored by keyboard and shows corresponding keyboard but this is not guaranteed. For example,
 * some keyboards may send non-ASCII character even if you set KeyboardType.Ascii.
 *
 * @param visualTransformation transforms the visual representation of the input value.
 * When [isSecret] is true this visual transformation will be applied. Default [OtpViewVisualTransformation]
 *
 * @param onAllDigitsFilled Indicates when all fields are filled.
 *
 * @param onPressedDelKey Indicates when delete key is pressed.
 *
 */
@Composable
fun OtpView(
    modifier: Modifier,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
    styles: OtpViewStyle = OtpViewStyles.rounded(),
    isSecret: Boolean = false,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Number,
    visualTransformation: VisualTransformation = PasswordVisualTransformation(),
    onAllDigitsFilled: (String) -> Unit,
    onPressedDelKey: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val animateError = remember { Animatable(initialValue = 0.0F) }
    LaunchedEffect(key1 = isError) {
        if (isError) {
            animateError.animateTo(
                targetValue = 0.dp.value,
                animationSpec = ShakeAnimation,
            )
        }
    }
    val textFieldPins = getClearFields(otpSize = styles.otpSize)

    BoxWithConstraints(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 0.dp,
                Alignment.CenterHorizontally
            ),
            modifier = Modifier.otpFocusManager(
                focusManager = focusManager,
                onPressedDelKey = onPressedDelKey
            ),
        ) {
            textFieldPins.forEachIndexed { index, textField ->
                OtpDigit(
                    textInput = textFieldPins[index].value,
                    isSecret = isSecret,
                    isError = isError,
                    isEnabled = isEnabled,
                    colors = colors,
                    styles = styles,
                    position = index,
                    size = textFieldPins.size,
                    modifier = modifier
                        .offset(x = animateError.value.dp)
                        .vibrateFeedback(isError = isError)
                        .weight(1f),
                    keyboardType = keyboardType,
                    visualTransformation = visualTransformation,
                    onTextChange = { clearPressed, textChanged ->
                        if(clearPressed) {
                            textField.value = textChanged
                        } else {
                            if(textChanged.isNotEmpty()) {
                                textField.value = textChanged
                            }
                            if (
                                keyboardType == KeyboardType.Text ||
                                keyboardType == KeyboardType.Ascii ||
                                keyboardType == KeyboardType.NumberPassword ||
                                keyboardType == KeyboardType.Uri
                            ) {
                                focusManager.moveFocus(FocusDirection.Right)
                            }
                        }
                        allFieldsIsFilled(fieldList = textFieldPins, otpSize = styles.otpSize) {
                            onAllDigitsFilled(it)
                        }
                    },
                    onPasteEvent = {
                        pasteText(token = it, fieldList = textFieldPins)
                        focusManager.clearFocus()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun OtpDigit(
    textInput: String,
    isSecret: Boolean,
    isError: Boolean,
    isEnabled: Boolean,
    position: Int,
    size: Int,
    modifier: Modifier,
    styles: OtpViewStyle,
    keyboardType: KeyboardType = KeyboardType.Number,
    visualTransformation: VisualTransformation,
    colors: TextFieldColors,
    onTextChange: (Boolean, String) -> Unit,
    onPasteEvent: (String) -> Unit
) {
    var text by remember {
        mutableStateOf(textInput)
    }
    var clearPressed by remember {
        mutableStateOf(false)
    }

    val fieldSemantic = Modifier.semantics {
        contentDescription = "$position / $size." +
                if (isSecret && text.isNotEmpty()) "Marcador" else ""
    }
    val interactionSource = remember { MutableInteractionSource() }
    val textStyle = styles.textStyle.copy(
        textAlign = TextAlign.Center,
        color = colors.labelColor(
            enabled = isEnabled,
            error = isError,
            interactionSource = remember { MutableInteractionSource() }
        ).value,
        fontSize = if(isSecret) 14.sp else styles.textStyle.fontSize
    )
    BasicTextField(
        value = textInput,
        onValueChange = {
            if(it.length == 4) {
               onPasteEvent(it)
            } else {
                text = it.takeLast(1)
            }
            onTextChange(clearPressed, text)
        },
        enabled = isEnabled,
        textStyle = textStyle,
        modifier = modifier
            .then(fieldSemantic)
            .onKeyEvent {
                clearPressed = it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                if (it.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL) {
                    text = ""
                    onTextChange(clearPressed, text)
                    clearPressed = false
                }
                false
            }
            .size(56.dp)
            .background(
                color = colors.backgroundColor(enabled = isEnabled).value,
                shape = styles.shape
            )
            .borderFocusActive(
                shape = styles.shape,
                color = colors.labelColor(
                    enabled = isEnabled,
                    error = isError,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ).value
            ),
        visualTransformation = if (isSecret) OtpViewVisualTransformation() else VisualTransformation.None,
        interactionSource = interactionSource,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        readOnly = false,
        cursorBrush = SolidColor(colors.cursorColor(isError = isError).value),
    ) { innerTextField ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier,
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = text,
                visualTransformation = if (isSecret) visualTransformation else VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                isError = isError,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(0.dp),
                colors = colors,
            )
        }
    }
}

val ShakeAnimation = keyframes {
    0.0F at 0
    20.0F at 80
    -20.0F at 120
    10.0F at 160
    -10.0F at 200
    5.0F at 240
    0.0F at 280
}

@Composable
internal fun getClearFields(otpSize: OtpSize) = remember {
    when (otpSize) {
        OtpSize.SIX -> listOf(
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(""),
        )
        OtpSize.FOUR -> listOf(
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(""),
            mutableStateOf(""),
        )
    }
}

internal fun allFieldsIsFilled(
    fieldList: List<MutableState<String>>,
    otpSize: OtpSize,
    onComplete: (String) -> Unit,
) {
    val filled = fieldList.filter {
        it.value.isNotEmpty()
    }
    if (filled.size == otpSize.size) {
        var text = ""
        fieldList.forEach {
            text += it.value
        }
        onComplete(text)
    }
}

internal fun pasteText(token: String?, fieldList: List<MutableState<String>>) {
    token ?: return
    for (i in token.indices) {
        fieldList[i].value = token[i].toString()
    }
}

fun Modifier.otpFocusManager(
    focusManager: FocusManager,
    onPressedDelKey: () -> Unit
) = composed {
    onKeyEvent {
        when {
            it.nativeKeyEvent.keyCode != KeyEvent.KEYCODE_DEL -> {
                focusManager.moveFocus(FocusDirection.Right)
                return@onKeyEvent false
            }
            else -> {
                onPressedDelKey()
                focusManager.moveFocus(FocusDirection.Left)
                return@onKeyEvent false
            }
        }
    }
}

fun Modifier.borderFocusActive(
    shape: Shape,
    color: Color
) = composed {
    val isFocusable = remember {
        mutableStateOf(false)
    }
    Modifier
        .onFocusEvent {
            isFocusable.value = it.isFocused
        }
        .border(
            width = if (isFocusable.value) 2.dp else 1.dp,
            color = color,
            shape = shape,
        )
}

fun Modifier.vibrateFeedback(
    duration: Long = 300L,
    isError: Boolean
) = composed {
    val context = LocalContext.current
    if (isError) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).vibrate(
                CombinedVibration.createParallel(
                    VibrationEffect.createOneShot(
                        duration,
                        VibrationEffect.DEFAULT_AMPLITUDE,
                    ),
                ),
            )
        } else {
            (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator).let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(
                        VibrationEffect.createOneShot(
                            duration,
                            VibrationEffect.DEFAULT_AMPLITUDE,
                        ),
                    )
                } else {
                    it.vibrate(duration)
                }
            }
        }
    }
    this
}

enum class OtpSize(val size: Int) {
    SIX(6),
    FOUR(4),
}