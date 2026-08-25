package com.arivux.laboratory.physics.simple_pendulum

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class SimplePendulumLab {
    val state = SimplePendulumState()
    val engine = SimplePendulumEngine(state)
    val validator = SimplePendulumValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Simple Pendulum Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val stand = PendulumStandComponent("stand_primary", Vector2D(300f, 100f))
        val bob = PendulumBobComponent("bob_primary", Vector2D(280f, 325f)) // Centered at pivot + 1.5m scale

        val stateManager = labEngine.stateManager
        stateManager.addObject(stand)
        stateManager.addObject(bob)

        // Configure initial values
        state.lengthL = 1.5f
        state.gravityG = 9.8f
        state.angleRad = 0f
        state.angularVelocity = 0f
        state.oscillationsCount = 0

        labEngine.notifyStateChanged()
    }
}
