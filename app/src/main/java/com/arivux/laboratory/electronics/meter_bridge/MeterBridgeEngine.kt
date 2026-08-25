package com.arivux.laboratory.electronics.meter_bridge

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class MeterBridgeEngine(
    val state: MeterBridgeState = MeterBridgeState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val board = objects.firstOrNull { it is MeterBridgeBoardComponent } as? MeterBridgeBoardComponent ?: return

        // Read jockey position
        state.jockeyPositionCm = board.currentValue

        val r = state.resistanceR
        val x = state.unknownResistanceX
        val l = state.jockeyPositionCm

        if (l > 0.1f && l < 99.9f) {
            // Galvanometer current equation: Ig = (R * (100 - l) - X * l) / 1000f
            val current = (r * (100f - l) - x * l) / 1000f
            state.galvanometerCurrent = current

            state.isNullPointFound = kotlin.math.abs(current) < 0.005f
            state.calculatedX = r * (100f - l) / l
        } else {
            state.galvanometerCurrent = 1.0f
            state.isNullPointFound = false
            state.calculatedX = 0f
        }
    }
}
