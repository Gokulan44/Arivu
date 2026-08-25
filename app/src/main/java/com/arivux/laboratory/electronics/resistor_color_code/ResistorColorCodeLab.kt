package com.arivux.laboratory.electronics.resistor_color_code

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class ResistorColorCodeLab {
    val state = ResistorColorCodeState()
    val engine = ResistorColorCodeEngine(state)
    val validator = ResistorColorCodeValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val resistor = ColorCodedResistorComponent("resistor_color", Vector2D(300f, 250f), state)

        val stateManager = labEngine.stateManager
        stateManager.addObject(resistor)

        // Configure target parameters: 4700 Ω +/- 5% (Yellow, Violet, Red, Gold)
        state.targetResistance = 4700f
        state.targetTolerancePercent = 5f

        // Initial color state (1000 Ω +/- 5%)
        state.currentBand1Color = "Brown"
        state.currentBand2Color = "Black"
        state.currentBand3Color = "Red"
        state.currentBand4Color = "Gold"

        labEngine.notifyStateChanged()
    }
}
