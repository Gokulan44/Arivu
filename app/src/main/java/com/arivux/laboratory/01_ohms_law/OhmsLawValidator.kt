package com.arivux.laboratory.ohms_law

class OhmsLawValidator(
    private val state: OhmsLawState
) {
    fun checkCircuitValidity(): Boolean {
        // Circuit is valid if there are no short circuits, no blown components,
        // and a healthy current flow is detected
        val isHealthy = state.measuredCurrent > 0.01f && 
                        !state.isBurnedOut && 
                        !state.isShortCircuit
        
        state.circuitConnectedCorrectly = isHealthy
        return isHealthy
    }

    fun logDataPoint(voltage: Float, current: Float) {
        if (state.voltageCurrentLog.none { it.first == voltage }) {
            state.voltageCurrentLog.add(Pair(voltage, current))
        }

        // Sweeping objective completed if at least 3 distinct voltage points are logged
        if (state.voltageCurrentLog.size >= 3) {
            state.sweepCompleted = true
        }
    }

    fun verifyCalculatedResistance(nominalValue: Float): Boolean {
        if (state.voltageCurrentLog.isEmpty()) return false
        
        // Compute average R = V/I from the log
        val calculatedResistances = state.voltageCurrentLog.map { (v, i) ->
            if (i > 0.001f) v / i else 0f
        }.filter { it > 0f }

        if (calculatedResistances.isEmpty()) return false

        val avgCalculated = calculatedResistances.average().toFloat()
        // Allow up to 5% tolerance error
        val margin = nominalValue * 0.05f
        return kotlin.math.abs(avgCalculated - nominalValue) <= margin
    }
}
