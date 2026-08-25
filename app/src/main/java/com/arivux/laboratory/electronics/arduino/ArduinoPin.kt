package com.arivux.laboratory.electronics.arduino

enum class PinMode {
    INPUT, OUTPUT, INPUT_PULLUP
}

enum class PinState {
    LOW, HIGH
}

data class ArduinoPin(
    val id: String,
    val isDigital: Boolean,
    var mode: PinMode = PinMode.INPUT,
    var digitalState: PinState = PinState.LOW,
    var analogValue: Int = 0 // 0 to 1023
)
