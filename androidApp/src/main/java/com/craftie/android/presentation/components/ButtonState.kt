package com.craftie.android.presentation.components

sealed class ButtonState {
    object IDLE: ButtonState()
    object PRESSED: ButtonState()
}
