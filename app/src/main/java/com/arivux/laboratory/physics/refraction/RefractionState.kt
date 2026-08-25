package com.arivux.laboratory.physics.refraction

data class RefractionState(
    var laserAngleDegrees: Float = 30.0f,
    var refractiveIndexN: Float = 1.5f, // Glass slab index

    var incidentAngleDegrees: Float = 30.0f,
    var refractedAngleDegrees: Float = 0f,

    var isSnellsLawVerified: Boolean = false,

    // Ray tracing coordinates
    var rayStartX: Float = 0f,
    var rayStartY: Float = 0f,
    var rayEntryX: Float = 0f,
    var rayEntryY: Float = 0f,
    var rayExitX: Float = 0f,
    var rayExitY: Float = 0f,
    var rayEndX: Float = 0f,
    var rayEndY: Float = 0f
)
