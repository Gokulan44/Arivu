package com.arivux.laboratory.electronics.arduino_temperature

data class ArduinoTemperatureState(
    var isSensorConnected: Boolean = false,
    var sensorValueC: Float = 0f,
    var highestLogTempC: Float = 0f,
    var logCompleted: Boolean = false,
    var codeSketchSelected: String = "Temperature"
)
