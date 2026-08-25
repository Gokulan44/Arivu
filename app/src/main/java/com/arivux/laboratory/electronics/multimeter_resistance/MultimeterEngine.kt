package com.arivux.laboratory.electronics.multimeter_resistance

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class MultimeterEngine(
    val state: MultimeterState = MultimeterState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val meter = objects.firstOrNull { it is MultimeterComponent } as? MultimeterComponent ?: return
        val resistor = objects.firstOrNull { it is MultimeterResistorComponent } as? MultimeterResistorComponent

        // Read scale index from dial slider
        val scaleIndex = meter.currentValue.toInt()
        val scales = listOf(200f, 2000f, 20000f, 200000f)
        state.selectedScale = scales.getOrElse(scaleIndex) { 200f }

        // Check probe connections
        if (resistor != null) {
            val connectedBlack = wires.any {
                (it.fromComponentId == meter.id && it.fromTerminalId == "black" && it.toComponentId == resistor.id) ||
                (it.toComponentId == meter.id && it.toTerminalId == "black" && it.fromComponentId == resistor.id)
            }
            val connectedRed = wires.any {
                (it.fromComponentId == meter.id && it.fromTerminalId == "red" && it.toComponentId == resistor.id) ||
                (it.toComponentId == meter.id && it.toTerminalId == "red" && it.fromComponentId == resistor.id)
            }
            state.areProbesConnected = connectedBlack && connectedRed
        } else {
            state.areProbesConnected = false
        }

        if (state.areProbesConnected) {
            val r = state.resistanceR
            val scale = state.selectedScale

            if (r > scale) {
                state.measuredValueText = "OL"
                state.measuredValueFloat = 0f
            } else {
                state.measuredValueFloat = r
                state.measuredValueText = when (state.selectedScale) {
                    200f -> String.format("%.1f Ω", r)
                    2000f -> String.format("%.3f kΩ", r / 1000f)
                    20000f -> String.format("%.2f kΩ", r / 1000f)
                    20000f -> String.format("%.1f kΩ", r / 1000f)
                    else -> String.format("%.0f Ω", r)
                }
            }

            // For 1500Ω, 2000Ω (2k) is the optimal scale
            state.scaleSettingOptimal = (state.selectedScale == 2000f)
        } else {
            state.measuredValueText = "OL"
            state.measuredValueFloat = 0f
            state.scaleSettingOptimal = false
        }
    }
}
