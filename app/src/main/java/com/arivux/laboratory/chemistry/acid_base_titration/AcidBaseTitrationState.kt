package com.arivux.laboratory.chemistry.acid_base_titration

import com.arivux.laboratory.chemistry.SolutionState

data class AcidBaseTitrationState(
    val beakerSolution: SolutionState = SolutionState(),
    var buretteVolumeMl: Float = 50.0f,
    var baseMolarity: Float = 0.1f,
    var acidMolarity: Float = 0.1f,
    var indicatorAdded: Boolean = false,
    var solutionColorHex: String = "#D0E0FF", // Clear liquid
    
    // Valve setting: 0 = closed, 1 = slow drip, 2 = medium, 3 = streaming
    var stopcockFlowRateIndex: Int = 0,
    var totalBaseAddedMl: Float = 0f,
    
    var titrationCompleted: Boolean = false,
    var overTitrated: Boolean = false
)
