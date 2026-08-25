package com.arivux.laboratory.interaction

interface ButtonObject {
    var isPressed: Boolean

    fun press() {
        isPressed = true
        onPressStateChanged(true)
    }

    fun release() {
        isPressed = false
        onPressStateChanged(false)
    }

    fun onPressStateChanged(pressed: Boolean)
}
