package com.arivux.laboratory.electronics.potentiometer

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class PotentiometerLab {
    val state = PotentiometerState()
    val engine = PotentiometerEngine(state)
    val validator = PotentiometerValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val bPrimary = PrimaryBatteryComponent("primary_battery", Vector2D(50f, 50f), 4.0f)
        val cSecondary = SecondaryCellComponent("secondary_cell", Vector2D(50f, 150f), 1.5f)
        val board = PotentiometerBoardComponent("potentiometer_board", Vector2D(50f, 230f))
        val galvo = PotentiometerGalvanometerComponent("galvo_primary", Vector2D(400f, 100f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(bPrimary)
        stateManager.addObject(cSecondary)
        stateManager.addObject(board)
        stateManager.addObject(galvo)

        // Configure initial values
        state.primaryEMF = 4.0f
        state.secondaryEMF = 1.5f
        state.wireTotalLengthCm = 400f
        state.rheostatResistance = 10f
        state.wireResistancePerCm = 0.05f
        state.jockeyPositionCm = 200f

        labEngine.notifyStateChanged()
    }
}
