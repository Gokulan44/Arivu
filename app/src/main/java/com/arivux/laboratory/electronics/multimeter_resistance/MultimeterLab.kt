package com.arivux.laboratory.electronics.multimeter_resistance

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class MultimeterLab {
    val state = MultimeterState()
    val engine = MultimeterEngine(state)
    val validator = MultimeterValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val resistor = MultimeterResistorComponent("resistor_target", Vector2D(150f, 250f), 1500.0f)
        val multimeter = MultimeterComponent("multimeter_primary", Vector2D(350f, 150f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(resistor)
        stateManager.addObject(multimeter)

        // Configure initial values
        state.resistanceR = 1500f
        state.selectedScale = 200f

        labEngine.notifyStateChanged()
    }
}
