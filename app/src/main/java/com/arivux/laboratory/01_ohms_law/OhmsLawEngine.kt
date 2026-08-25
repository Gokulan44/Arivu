package com.arivux.laboratory.ohms_law

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.SwitchComponent

class OhmsLawEngine(
    val state: OhmsLawState = OhmsLawState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        if (state.isBurnedOut) {
            resetMeasurements()
            return
        }

        // 1. Trace connectivity path
        val hasPath = checkClosedLoop(objects, wires)

        if (hasPath) {
            // Find switch status
            val switchComponent = objects.firstOrNull { it is SwitchComponent } as? SwitchComponent
            val isSwitchClosed = switchComponent?.isOpen == false

            if (isSwitchClosed) {
                // Determine battery voltage and resistor rating
                val batteryObj = objects.firstOrNull { it.type == "Battery" }
                val resistorObj = objects.firstOrNull { it.type == "Resistor" }

                val v = state.batteryVoltage
                val r = if (resistorObj != null) state.resistance else 0.1f // direct path represents small resistance

                if (resistorObj == null) {
                    // Short circuit!
                    state.isShortCircuit = true
                    state.measuredCurrent = 50f // Max safety cutoff current
                    state.measuredVoltage = v
                } else {
                    state.isShortCircuit = false
                    // Solve Ohm's Law: I = V / R
                    val current = v / r
                    state.measuredCurrent = current
                    state.measuredVoltage = current * r
                    
                    // Solve Power: P = V * I
                    val power = v * current
                    state.powerDissipated = power

                    // Check thermal burnout (Power rating limit is 2W in config)
                    if (power > 2.0f) {
                        state.isBurnedOut = true
                        state.measuredCurrent = 0f
                        state.measuredVoltage = 0f
                    }
                }
            } else {
                // Closed loop exists, but switch is open
                resetMeasurements()
            }
        } else {
            // No closed loop
            resetMeasurements()
        }

        // Apply current flow calculations to wires for visual indicators
        for (wire in wires) {
            wire.currentFlow = if (state.isShortCircuit) 5.0f else if (state.measuredCurrent > 0f) 1.0f else 0f
        }
    }

    private fun checkClosedLoop(objects: List<LabObject>, wires: List<Wire>): Boolean {
        val battery = objects.firstOrNull { it.type == "Battery" } ?: return false
        val resistor = objects.firstOrNull { it.type == "Resistor" } ?: return false

        // Simple adjacency checking of terminals to confirm a connected loop.
        // A complete loop requires:
        // Battery (pos) connected to Switch/Resistor, and back to Battery (neg)
        val posConnected = isTerminalConnectedToAny(battery.id, "pos", wires)
        val negConnected = isTerminalConnectedToAny(battery.id, "neg", wires)

        return posConnected && negConnected
    }

    private fun isTerminalConnectedToAny(componentId: String, terminalId: String, wires: List<Wire>): Boolean {
        return wires.any {
            (it.fromComponentId == componentId && it.fromTerminalId == terminalId) ||
            (it.toComponentId == componentId && it.toTerminalId == terminalId)
        }
    }

    private fun resetMeasurements() {
        state.measuredCurrent = 0f
        state.measuredVoltage = 0f
        state.powerDissipated = 0f
        state.isShortCircuit = false
    }
}
