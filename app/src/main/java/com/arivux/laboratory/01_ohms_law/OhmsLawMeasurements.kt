package com.arivux.laboratory.ohms_law

class OhmsLawMeasurements(
    private val state: OhmsLawState
) {
    fun getVoltmeterReading(): String {
        if (state.isBurnedOut) return "0.00 V (BURNED)"
        if (state.isShortCircuit) return "0.00 V"
        return String.format("%.2f V", state.measuredVoltage)
    }

    fun getAmmeterReading(): String {
        if (state.isBurnedOut) return "0.00 A (BURNED)"
        if (state.isShortCircuit) return "OVERLOAD (50A)"
        return String.format("%.3f A", state.measuredCurrent)
    }

    fun getPowerReading(): String {
        if (state.isBurnedOut) return "0.00 W"
        return String.format("%.2f W", state.powerDissipated)
    }
}
