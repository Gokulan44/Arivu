package com.arivux.laboratory.electronics.potentiometer

class PotentiometerValidator(
    private val state: PotentiometerState
) {
    fun verifyBalancePointFound(): Boolean {
        // Target balance length for ES = 1.5V and k = 0.00667 V/cm is 225 cm, tolerance 2 cm
        return state.isBalanced && kotlin.math.abs(state.balanceLengthCm - 225f) <= 2.0f
    }
}
