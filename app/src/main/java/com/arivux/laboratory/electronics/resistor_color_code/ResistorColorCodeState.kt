package com.arivux.laboratory.electronics.resistor_color_code

data class ResistorColorCodeState(
    var targetResistance: Float = 4700f, // 4.7k Ω
    var targetTolerancePercent: Float = 5f, // Gold

    var currentBand1Color: String = "Brown", // 1
    var currentBand2Color: String = "Black", // 0
    var currentBand3Color: String = "Red",   // x100 = 1000Ω
    var currentBand4Color: String = "Gold",  // 5%

    var calculatedResistance: Float = 1000f,
    var calculatedTolerancePercent: Float = 5f,

    var isResistanceCorrect: Boolean = false,
    var isToleranceCorrect: Boolean = false
)
