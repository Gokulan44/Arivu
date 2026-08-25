package com.arivux.laboratory.chemistry

data class SolutionState(
    var totalVolumeMl: Float = 0f,
    var molesH: Float = 0f,
    var molesOH: Float = 0f,
    var ph: Float = 7.0f
) {
    fun addAcid(volumeMl: Float, molarity: Float) {
        val liters = volumeMl / 1000f
        molesH += molarity * liters
        totalVolumeMl += volumeMl
        solveReaction()
    }

    fun addBase(volumeMl: Float, molarity: Float) {
        val liters = volumeMl / 1000f
        molesOH += molarity * liters
        totalVolumeMl += volumeMl
        solveReaction()
    }

    fun reset() {
        totalVolumeMl = 0f
        molesH = 0f
        molesOH = 0f
        ph = 7.0f
    }

    private fun solveReaction() {
        if (totalVolumeMl <= 0f) {
            ph = 7.0f
            return
        }

        // Neutralization reaction: H+ + OH- -> H2O
        val neutralMoles = kotlin.math.min(molesH, molesOH)
        molesH -= neutralMoles
        molesOH -= neutralMoles

        val totalVolumeLiters = totalVolumeMl / 1000f

        ph = when {
            molesH > molesOH -> {
                val concentrationH = molesH / totalVolumeLiters
                val calculatedPh = -kotlin.math.log10(concentrationH.toDouble()).toFloat()
                calculatedPh.coerceIn(0f, 7f)
            }
            molesOH > molesH -> {
                val concentrationOH = molesOH / totalVolumeLiters
                val calculatedPoh = -kotlin.math.log10(concentrationOH.toDouble()).toFloat()
                (14f - calculatedPoh).coerceIn(7f, 14f)
            }
            else -> 7.0f
        }
    }
}

class ChemistryReactionEngine {
    fun react(solution: SolutionState, addedAcidVolume: Float, acidMolarity: Float) {
        solution.addAcid(addedAcidVolume, acidMolarity)
    }

    fun reactBase(solution: SolutionState, addedBaseVolume: Float, baseMolarity: Float) {
        solution.addBase(addedBaseVolume, baseMolarity)
    }
}
