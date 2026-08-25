package com.arivux.laboratory.electronics.kirchhoff_laws

data class KirchhoffState(
    var batteryVoltageV1: Float = 6.0f,
    var batteryVoltageV2: Float = 9.0f,
    var resistanceR1: Float = 100.0f,
    var resistanceR2: Float = 200.0f,
    var resistanceR3: Float = 150.0f,

    // Solved branch currents
    var currentI1: Float = 0f,
    var currentI2: Float = 0f,
    var currentI3: Float = 0f,

    // Solved component voltages
    var voltageR1: Float = 0f,
    var voltageR2: Float = 0f,
    var voltageR3: Float = 0f,

    var isLoop1Connected: Boolean = false,
    var isLoop2Connected: Boolean = false,
    
    // Milestones
    var kclVerified: Boolean = false,
    var kvlVerified: Boolean = false
)
