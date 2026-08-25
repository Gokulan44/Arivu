package com.arivux.laboratory.physics.convex_lens

class ConvexLensValidator(
    private val state: ConvexLensState
) {
    fun verifyFocusAchieved(): Boolean {
        return state.isFocused
    }

    fun verifyFocalLengthCalculation(userValue: Float): Boolean {
        // Target focal length is 20cm, tolerance 1cm
        return kotlin.math.abs(userValue - state.focalLengthF) <= 1.0f
    }
}
