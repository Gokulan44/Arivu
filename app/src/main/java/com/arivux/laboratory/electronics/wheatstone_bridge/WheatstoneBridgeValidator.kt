package com.arivux.laboratory.electronics.wheatstone_bridge

class WheatstoneBridgeValidator(
    private val state: WheatstoneBridgeState
) {
    fun verifyBridgeBalanced(): Boolean {
        return state.isBalanced && kotlin.math.abs(state.calculatedS - state.resistanceS) < 2.0f
    }
}
