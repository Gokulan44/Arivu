package com.arivux.laboratory.electronics.kirchhoff_laws

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class KirchhoffEngine(
    val state: KirchhoffState = KirchhoffState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val b1 = objects.firstOrNull { it.id == "battery_1" }
        val b2 = objects.firstOrNull { it.id == "battery_2" }

        state.isLoop1Connected = b1 != null && isBatteryConnected(b1.id, wires)
        state.isLoop2Connected = b2 != null && isBatteryConnected(b2.id, wires)

        if (state.isLoop1Connected || state.isLoop2Connected) {
            val r1 = state.resistanceR1
            val r2 = state.resistanceR2
            val r3 = state.resistanceR3

            val v1 = if (state.isLoop1Connected) state.batteryVoltageV1 else 0f
            val v2 = if (state.isLoop2Connected) state.batteryVoltageV2 else 0f

            // Mesh Current Equations:
            // I1 * (R1 + R3) - I2 * R3 = V1
            // -I1 * R3 + I2 * (R2 + R3) = -V2
            val r13 = r1 + r3
            val r23 = r2 + r3
            val det = r13 * r23 - r3 * r3

            if (det > 0.001f) {
                state.currentI1 = (v1 * r23 - v2 * r3) / det
                state.currentI2 = (-v2 * r13 + v1 * r3) / det
                state.currentI3 = state.currentI1 - state.currentI2

                // Calculate voltages
                state.voltageR1 = state.currentI1 * r1
                state.voltageR2 = state.currentI2 * r2
                state.voltageR3 = state.currentI3 * r3
            } else {
                resetMeasurements()
            }
        } else {
            resetMeasurements()
        }

        // Pulse indicators on wires
        for (wire in wires) {
            wire.currentFlow = if (kotlin.math.abs(state.currentI1) > 0.001f || kotlin.math.abs(state.currentI2) > 0.001f) 1.0f else 0f
        }
    }

    private fun isBatteryConnected(batteryId: String, wires: List<Wire>): Boolean {
        val hasPos = wires.any { it.fromComponentId == batteryId && it.fromTerminalId == "pos" || it.toComponentId == batteryId && it.toTerminalId == "pos" }
        val hasNeg = wires.any { it.fromComponentId == batteryId && it.fromTerminalId == "neg" || it.toComponentId == batteryId && it.toTerminalId == "neg" }
        return hasPos && hasNeg
    }

    private fun resetMeasurements() {
        state.currentI1 = 0f
        state.currentI2 = 0f
        state.currentI3 = 0f
        state.voltageR1 = 0f
        state.voltageR2 = 0f
        state.voltageR3 = 0f
    }
}
