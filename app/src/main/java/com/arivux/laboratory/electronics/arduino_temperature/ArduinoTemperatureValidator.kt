package com.arivux.laboratory.electronics.arduino_temperature

class ArduinoTemperatureValidator(
    private val state: ArduinoTemperatureState
) {
    fun verifyTempLogSuccess(): Boolean {
        return state.isSensorConnected && state.logCompleted
    }
}
