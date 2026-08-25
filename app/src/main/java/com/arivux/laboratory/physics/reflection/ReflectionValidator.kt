package com.arivux.laboratory.physics.reflection

class ReflectionValidator(
    private val state: ReflectionState
) {
    fun verifyReflectionLaw(): Boolean {
        // i = r
        val diff = state.incidentAngleDegrees - state.reflectedAngleDegrees
        val correct = kotlin.math.abs(diff) < 0.01f
        state.isReflectionLawSatisfied = correct
        return correct
    }
}
