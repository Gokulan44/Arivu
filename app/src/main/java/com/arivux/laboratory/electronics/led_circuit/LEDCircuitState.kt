package com.arivux.laboratory.electronics.led_circuit

data class LEDCircuitState(
    var batteryVoltage: Float = 9.0f,
    var resistance: Float = 220.0f,
    var currentFlow: Float = 0f,
    var ledGlowPercentage: Float = 0f,
    
    var isBurnedOut: Boolean = false,
    var isShortCircuit: Boolean = false,
    
    var circuitConnectedCorrectly: Boolean = false,
    var glowCompleted: Boolean = false
)
