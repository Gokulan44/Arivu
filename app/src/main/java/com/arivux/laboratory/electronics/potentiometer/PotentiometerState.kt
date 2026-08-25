package com.arivux.laboratory.electronics.potentiometer

data class PotentiometerState(
    var primaryEMF: Float = 4.0f,
    var secondaryEMF: Float = 1.5f,
    var wireTotalLengthCm: Float = 400f,
    var rheostatResistance: Float = 10f,
    var wireResistancePerCm: Float = 0.05f,
    
    var jockeyPositionCm: Float = 200f, // 0 to 400 cm
    var galvanometerCurrent: Float = 1.0f,
    var isBalanced: Boolean = false,
    var balanceLengthCm: Float = 0f
)
