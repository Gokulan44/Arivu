package com.arivux.laboratory.electronics.led_circuit

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.SwitchComponent

class LEDCircuitEngine(
    val state: LEDCircuitState = LEDCircuitState()
) : DomainSolver {

    private val forwardVoltage = 1.8f // 1.8V threshold for Red LED

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        if (state.isBurnedOut) {
            state.currentFlow = 0f
            state.ledGlowPercentage = 0f
            return
        }

        val hasLoop = checkCircuitLoop(objects, wires)
        val switchKey = objects.firstOrNull { it is SwitchComponent } as? SwitchComponent
        val isSwitchClosed = switchKey?.isOpen == false

        if (hasLoop && isSwitchClosed) {
            // LED is active
            val batteryObj = objects.firstOrNull { it.type == "Battery" }
            val resistorObj = objects.firstOrNull { it.type == "Resistor" }

            val v = state.batteryVoltage
            val r = if (resistorObj != null) state.resistance else 0.1f // direct connection represents small resistance

            if (resistorObj == null) {
                // Short circuit burnout!
                state.isShortCircuit = true
                state.isBurnedOut = true
                state.currentFlow = 0f
                state.ledGlowPercentage = 0f
            } else {
                state.isShortCircuit = false
                
                // Solve branch current: I = (V - Vd) / R
                val current = (v - forwardVoltage) / r
                
                if (current > 0f) {
                    state.currentFlow = current
                    
                    // Optimal current is 20mA (0.02A) for max brightness
                    state.ledGlowPercentage = (current / 0.02f * 100f).coerceIn(0f, 100f)
                    
                    // Check safe threshold limit (40mA)
                    if (current > 0.040f) {
                        state.isBurnedOut = true
                        state.ledGlowPercentage = 0f
                        state.currentFlow = 0f
                    }
                } else {
                    state.currentFlow = 0f
                    state.ledGlowPercentage = 0f
                }
            }
        } else {
            state.currentFlow = 0f
            state.ledGlowPercentage = 0f
        }

        // Apply current flow to wires
        for (wire in wires) {
            wire.currentFlow = if (state.currentFlow > 0f) 1.0f else 0f
        }
    }

    private fun checkCircuitLoop(objects: List<LabObject>, wires: List<Wire>): Boolean {
        val battery = objects.firstOrNull { it.type == "Battery" } ?: return false
        val led = objects.firstOrNull { it is LEDComponent } ?: return false

        // A closed loop requires anode connected to positive, and cathode connected to negative
        val positiveConnected = isTerminalConnectedToAny(battery.id, "pos", wires)
        val negativeConnected = isTerminalConnectedToAny(battery.id, "neg", wires)

        return positiveConnected && negativeConnected
    }

    private fun isTerminalConnectedToAny(componentId: String, terminalId: String, wires: List<Wire>): Boolean {
        return wires.any {
            (it.fromComponentId == componentId && it.fromTerminalId == terminalId) ||
            (it.toComponentId == componentId && it.toTerminalId == terminalId)
        }
    }
}
