package com.arivux.laboratory.mathematics.coordinate_geometry

data class CoordinateGeometryState(
    var ax: Float = 0f,
    var ay: Float = 0f,
    var bx: Float = 5f,
    var by: Float = 5f,
    
    var calculatedDistance: Float = 0f,
    var calculatedSlope: Float = 0f,
    var calculatedIntercept: Float = 0f,
    var lineEquation: String = "",
    
    var pointsPlacedCorrectly: Boolean = false,
    var slopeVerified: Boolean = false
)
