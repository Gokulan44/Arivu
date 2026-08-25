package com.arivux.laboratory.electronics.wheatstone_bridge

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class WheatstoneBridgeLab {
    val state = WheatstoneBridgeState()
    val engine = WheatstoneBridgeEngine(state)
    val validator = WheatstoneBridgeValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val p = BridgeResistorComponent("resistor_p", Vector2D(100f, 150f), 100.0f)
        val q = BridgeResistorComponent("resistor_q", Vector2D(400f, 150f), 200.0f)
        val r = VariableResistorComponent("resistor_r", Vector2D(100f, 350f))
        val s = BridgeResistorComponent("resistor_s", Vector2D(400f, 350f), 300.0f) // Unknown target
        val galvo = GalvanometerComponent("galvo_primary", Vector2D(250f, 250f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(p)
        stateManager.addObject(q)
        stateManager.addObject(r)
        stateManager.addObject(s)
        stateManager.addObject(galvo)

        // Configure initial values
        state.resistanceP = 100.0f
        state.resistanceQ = 200.0f
        state.resistanceR = 100.0f
        state.resistanceS = 300.0f

        labEngine.notifyStateChanged()
    }
}
