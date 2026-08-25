package com.arivux.laboratory.physics.reflection

data class ReflectionState(
    var laserAngleDegrees: Float = 30.0f,
    var mirrorRotationDegrees: Float = 0.0f,
    
    var incidentAngleDegrees: Float = 30.0f,
    var reflectedAngleDegrees: Float = 30.0f,
    
    var isReflectionLawSatisfied: Boolean = false,

    // Ray rendering coordinates
    var rayStartX: Float = 0f,
    var rayStartY: Float = 0f,
    var rayHitX: Float = 0f,
    var rayHitY: Float = 0f,
    var rayEndX: Float = 0f,
    var rayEndY: Float = 0f
)
