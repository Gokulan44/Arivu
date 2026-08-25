package com.arivux.laboratory.physics.simple_pendulum

class SimplePendulumValidator(
    private val state: SimplePendulumState
) {
    fun checkLengthConfigured(): Boolean {
        // Target length is 1.5m, tolerance 0.1m
        val isCorrect = kotlin.math.abs(state.lengthL - 1.5f) < 0.1f
        state.lengthConfigured = isCorrect
        return isCorrect
    }

    fun verifySwingCompleted(): Boolean {
        // Target is at least 10 oscillations
        val completed = state.oscillationsCount >= 10
        state.swingCompleted = completed
        return completed
    }

    fun verifyReleaseAngle(initialAngleDegrees: Float): Boolean {
        // Target release angle is 15 degrees, tolerance 2 degrees
        return kotlin.math.abs(initialAngleDegrees - 15f) <= 2f
    }
}
