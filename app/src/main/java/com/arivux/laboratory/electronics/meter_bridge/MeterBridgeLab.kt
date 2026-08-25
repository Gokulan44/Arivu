package com.arivux.laboratory.electronics.meter_bridge

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class MeterBridgeLab {
    val state = MeterBridgeState()
    val engine = MeterBridgeEngine(state)
    val validator = MeterBridgeValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val r = MeterBridgeResistorComponent("resistor_r", Vector2D(100f, 100f), 10.0f)
        val x = MeterBridgeResistorComponent("resistor_x", Vector2D(350f, 100f), 15.0f) // Unknown target
        val board = MeterBridgeBoardComponent("bridge_board", Vector2D(50f, 220f))
        val galvo = MeterBridgeGalvanometerComponent("galvo_primary", Vector2D(250f, 380f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(r)
        stateManager.addObject(x)
        stateManager.addObject(board)
        stateManager.addObject(galvo)

        // Configure initial values
        state.resistanceR = 10.0f
        state.unknownResistanceX = 15.0f
        state.jockeyPositionCm = 50.0f

        labEngine.notifyStateChanged()
    }
}
