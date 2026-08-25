package com.arivux.laboratory.electronics.potentiometer

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class PotentiometerEngine(
    val state: PotentiometerState = PotentiometerState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val board = objects.firstOrNull { it is PotentiometerBoardComponent } as? PotentiometerBoardComponent ?: return

        // Read jockey position
        state.jockeyPositionCm = board.currentValue

        // Wires check to see if secondary circuit is active
        val galvo = objects.firstOrNull { it is PotentiometerGalvanometerComponent }
        val active = galvo != null && wires.any { it.fromComponentId == galvo.id || it.toComponentId == galvo.id }

        if (active) {
            val rw = state.wireTotalLengthCm * state.wireResistancePerCm // 400 * 0.05 = 20 ohms
            val ip = state.primaryEMF / (rw + state.rheostatResistance) // 4.0 / (20 + 10) = 0.1333A
            val vw = ip * rw // 0.1333 * 20 = 2.667V
            val k = vw / state.wireTotalLengthCm // 2.667 / 400 = 0.00667 V/cm

            // Galvanometer current is proportional to V_jockey - E_secondary
            val current = (k * state.jockeyPositionCm - state.secondaryEMF) / 50f // Rg = 50 ohms
            state.galvanometerCurrent = current

            state.isBalanced = kotlin.math.abs(current) < 0.001f
            if (state.isBalanced) {
                state.balanceLengthCm = state.jockeyPositionCm
            }
        } else {
            state.galvanometerCurrent = 1.0f
            state.isBalanced = false
            state.balanceLengthCm = 0f
        }
    }
}
