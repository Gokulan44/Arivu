package com.arivux.laboratory.electronics.kirchhoff_laws

class KirchhoffValidator(
    private val state: KirchhoffState
) {
    fun verifyKCL(): Boolean {
        if (state.currentI1 == 0f && state.currentI2 == 0f) return false
        
        // KCL: current entering junction (node between R1, R2, R3) equals current leaving it
        // I1 = I2 + I3 => I1 - I2 - I3 = 0
        val diff = state.currentI1 - state.currentI2 - state.currentI3
        val verified = kotlin.math.abs(diff) < 0.001f
        state.kclVerified = verified
        return verified
    }

    fun verifyKVL(): Boolean {
        if (!state.isLoop1Connected && !state.isLoop2Connected) return false

        // Loop 1 KVL: V1 - V_R1 - V_R3 = 0
        val kvl1 = if (state.isLoop1Connected) {
            val sum = state.batteryVoltageV1 - state.voltageR1 - state.voltageR3
            kotlin.math.abs(sum) < 0.01f
        } else true

        // Loop 2 KVL: -V2 - V_R2 + V_R3 = 0 => V2 + V_R2 - V_R3 = 0 (taking sign into account)
        val kvl2 = if (state.isLoop2Connected) {
            val sum = state.batteryVoltageV2 - state.voltageR2 + state.voltageR3 // depending on current directions
            kotlin.math.abs(sum) < 0.01f || kotlin.math.abs(state.batteryVoltageV2 - state.voltageR2 - state.voltageR3) < 0.01f
        } else true

        val verified = kvl1 && kvl2
        state.kvlVerified = verified
        return verified
    }
}
