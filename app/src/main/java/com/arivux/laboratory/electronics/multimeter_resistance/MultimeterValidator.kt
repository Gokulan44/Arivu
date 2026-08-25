package com.arivux.laboratory.electronics.multimeter_resistance

class MultimeterValidator(
    private val state: MultimeterState
) {
    fun verifyMeasurementComplete(): Boolean {
        return state.areProbesConnected && 
               state.measuredValueFloat > 0f && 
               state.scaleSettingOptimal
    }
}
