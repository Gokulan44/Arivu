package com.arivux.laboratory.electronics.arduino_motion

data class ArduinoMotionState(
    var isSensorConnected: Boolean = false,
    var isBuzzerConnected: Boolean = false,
    var motionDetected: Boolean = false,
    var buzzerBeeping: Boolean = false,
    var alertTriggered: Boolean = false,
    var codeSketchSelected: String = "Motion"
)
