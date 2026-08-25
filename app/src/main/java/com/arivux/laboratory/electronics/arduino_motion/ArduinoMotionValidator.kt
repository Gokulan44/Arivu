package com.arivux.laboratory.electronics.arduino_motion

class ArduinoMotionValidator(
    private val state: ArduinoMotionState
) {
    fun verifyAlertSuccess(): Boolean {
        return state.isSensorConnected && state.isBuzzerConnected && state.alertTriggered
    }
}
