package com.arivux.laboratory.electronics.meter_bridge

class MeterBridgeValidator(
    private val state: MeterBridgeState
) {
    fun verifyNullPointFound(): Boolean {
        return state.isNullPointFound && kotlin.math.abs(state.calculatedX - state.unknownResistanceX) < 0.2f
    }
}
