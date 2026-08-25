package com.arivux.laboratory.electronics.arduino_led

class ArduinoLedValidator(
    private val state: ArduinoLedState
) {
    fun verifyBlinkSuccess(): Boolean {
        return state.isCircuitCorrect && state.isLedBlinking && state.blinkCompleted
    }
}
