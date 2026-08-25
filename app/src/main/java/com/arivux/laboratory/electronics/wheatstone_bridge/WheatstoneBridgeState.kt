package com.arivux.laboratory.electronics.wheatstone_bridge

data class WheatstoneBridgeState(
    var resistanceP: Float = 100.0f,
    var resistanceQ: Float = 200.0f,
    var resistanceR: Float = 100.0f, // Variable resistor
    var resistanceS: Float = 300.0f, // Unknown resistor target value

    var galvanometerCurrent: Float = 1.0f,
    var isBalanced: Boolean = false,
    var calculatedS: Float = 0f,
    var bridgeConnected: Boolean = false
)
