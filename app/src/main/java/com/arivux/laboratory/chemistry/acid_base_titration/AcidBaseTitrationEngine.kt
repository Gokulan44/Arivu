package com.arivux.laboratory.chemistry.acid_base_titration

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.chemistry.ColorChangeEngine

class AcidBaseTitrationEngine(
    val state: AcidBaseTitrationState = AcidBaseTitrationState()
) : DomainSolver {

    private val colorEngine = ColorChangeEngine()

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val burette = objects.firstOrNull { it is BuretteComponent } as? BuretteComponent ?: return
        val beaker = objects.firstOrNull { it is BeakerComponent } as? BeakerComponent ?: return

        // 1. Resolve stopcock valve setting to flow rate index (0 to 3)
        val flowRateIndex = burette.currentValue.toInt()
        state.stopcockFlowRateIndex = flowRateIndex

        val dripRateMlPerSec = when (flowRateIndex) {
            1 -> 0.2f  // Slow drip
            2 -> 0.8f  // Medium
            3 -> 2.5f  // Stream
            else -> 0f // Closed
        }

        if (dripRateMlPerSec > 0f && state.buretteVolumeMl > 0f) {
            val dripAmount = (dripRateMlPerSec * deltaTime).coerceAtMost(state.buretteVolumeMl)
            state.buretteVolumeMl -= dripAmount
            
            // Beaker receives the Base (NaOH)
            state.beakerSolution.addBase(dripAmount, state.baseMolarity)
            state.totalBaseAddedMl += dripAmount
        }

        // 2. Perform color rendering based on indicator and pH
        if (state.indicatorAdded) {
            state.solutionColorHex = colorEngine.getSolutionColor("phenolphthalein", state.beakerSolution.ph)
        } else {
            state.solutionColorHex = "#D0E0FF" // Clear liquid if no indicator is present
        }

        // 3. Track milestones (Neutralization equivalent point occurs near pH 7.0 / pale pink)
        val ph = state.beakerSolution.ph
        if (ph in 8.2f..9.0f) {
            state.titrationCompleted = true
            state.overTitrated = false
        } else if (ph > 9.5f) {
            state.titrationCompleted = false
            state.overTitrated = true
        } else {
            state.titrationCompleted = false
            state.overTitrated = false
        }
    }
}
