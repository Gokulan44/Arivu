package com.arivux.laboratory.electronics.meter_bridge

data class MeterBridgeState(
    var resistanceR: Float = 10.0f, // Known standard resistance
    var unknownResistanceX: Float = 15.0f, // Unknown resistance target
    var jockeyPositionCm: Float = 50.0f, // Slider position (0 - 100 cm)
    
    var galvanometerCurrent: Float = 1.0f,
    var isNullPointFound: Boolean = false,
    var calculatedX: Float = 0f
)
