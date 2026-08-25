package com.arivux.laboratory.electronics.wheatstone_bridge

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class WheatstoneBridgeEngine(
    val state: WheatstoneBridgeState = WheatstoneBridgeState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val varResistor = objects.firstOrNull { it is VariableResistorComponent } as? VariableResistorComponent ?: return

        // Update variable resistor R from slider
        state.resistanceR = varResistor.currentValue

        // Simple connection check: bridge is active if there are wires connected to galvanometer
        val galvo = objects.firstOrNull { it is GalvanometerComponent }
        state.bridgeConnected = galvo != null && wires.any {
            it.fromComponentId == galvo.id || it.toComponentId == galvo.id
        }

        if (state.bridgeConnected) {
            val p = state.resistanceP
            val q = state.resistanceQ
            val r = state.resistanceR
            val s = state.resistanceS

            // V_c = V_in * R / (P + R)
            // V_d = V_in * S / (Q + S)
            val vin = 5.0f
            val vc = vin * r / (p + r)
            val vd = vin * s / (q + s)

            // I_g = (V_c - V_d) / R_g (say R_g = 50 ohms)
            val current = (vc - vd) / 50f
            state.galvanometerCurrent = current

            // Balanced if current is close to 0
            state.isBalanced = kotlin.math.abs(current) < 0.001f
            state.calculatedS = r * (q / p)
        } else {
            state.galvanometerCurrent = 1.0f
            state.isBalanced = false
            state.calculatedS = 0f
        }
    }
}
