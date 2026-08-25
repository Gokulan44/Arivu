package com.arivux.laboratory.interaction

interface RotatableObject {
    var rotationDegrees: Float
    val maxRotationDegrees: Float
        get() = 360f
    val minRotationDegrees: Float
        get() = 0f

    fun rotate(deltaAngle: Float) {
        val nextAngle = rotationDegrees + deltaAngle
        rotationDegrees = nextAngle.coerceIn(minRotationDegrees, maxRotationDegrees)
    }

    fun rotateTo(targetAngle: Float) {
        rotationDegrees = targetAngle.coerceIn(minRotationDegrees, maxRotationDegrees)
    }
}
