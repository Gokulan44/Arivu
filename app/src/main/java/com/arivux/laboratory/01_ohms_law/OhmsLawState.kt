package com.arivux.laboratory.ohms_law

data class OhmsLawState(
    var batteryVoltage: Float = 5.0f,
    var resistance: Float = 100.0f,
    var measuredVoltage: Float = 0.0f,
    var measuredCurrent: Float = 0.0f,
    var powerDissipated: Float = 0.0f,
    var isBurnedOut: Boolean = false,
    var isShortCircuit: Boolean = false,
    var isAmmeterBlown: Boolean = false,
    
    // Swept data log (V, I) recorded by the student
    val voltageCurrentLog: MutableList<Pair<Float, Float>> = mutableListOf(),
    
    // Goal progress checks
    var circuitConnectedCorrectly: Boolean = false,
    var sweepCompleted: Boolean = false
)
