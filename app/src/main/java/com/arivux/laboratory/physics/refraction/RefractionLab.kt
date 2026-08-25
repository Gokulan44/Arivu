package com.arivux.laboratory.physics.refraction

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class RefractionLab {
    val state = RefractionState()
    val engine = RefractionEngine(state)
    val validator = RefractionValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val laser = RefractionLaserComponent("laser_source", Vector2D(100f, 200f))
        val slab = GlassSlabComponent("glass_slab", Vector2D(350f, 100f), 1.5f)

        val stateManager = labEngine.stateManager
        stateManager.addObject(laser)
        stateManager.addObject(slab)

        // Configure initial values
        state.laserAngleDegrees = 30.0f
        state.refractiveIndexN = 1.5f

        labEngine.notifyStateChanged()
    }
}
