package com.arivux.laboratory.electronics.arduino_led

data class ArduinoLedState(
    var isLedBlinking: Boolean = false,
    var blinkCount: Int = 0,
    var isCircuitCorrect: Boolean = false,
    var codeSketchSelected: String = "Blink",
    var blinkCompleted: Boolean = false
)
