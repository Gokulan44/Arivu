package com.arivux.laboratory.electronics.multimeter_resistance

data class MultimeterState(
    var resistanceR: Float = 1500f,
    
    // Scale settings: 200f, 2000f, 20000f, 200000f
    var selectedScale: Float = 200f,
    
    var measuredValueText: String = "OL",
    var measuredValueFloat: Float = 0f,
    
    var areProbesConnected: Boolean = false,
    var scaleSettingOptimal: Boolean = false
)
