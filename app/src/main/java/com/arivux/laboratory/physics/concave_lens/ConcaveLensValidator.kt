package com.arivux.laboratory.physics.concave_lens

class ConcaveLensValidator(
    private val state: ConcaveLensState
) {
    fun verifyFocusAchieved(): Boolean {
        return state.isFocused
    }

    fun verifyConcaveFocalLength(userValue: Float): Boolean {
        // Target is -15cm, tolerance 1cm
        return kotlin.math.abs(userValue - state.focalLengthF) <= 1.0f
    }
}
