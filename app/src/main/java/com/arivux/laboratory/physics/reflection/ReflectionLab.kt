package com.arivux.laboratory.physics.reflection

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class ReflectionLab {
    val state = ReflectionState()
    val engine = ReflectionEngine(state)
    val validator = ReflectionValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val laser = LaserSourceComponent("laser_source", Vector2D(100f, 200f))
        val mirror = MirrorComponent("mirror_primary", Vector2D(450f, 100f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(laser)
        stateManager.addObject(mirror)

        // Configure initial values
        state.laserAngleDegrees = 30.0f
        state.mirrorRotationDegrees = 0.0f

        labEngine.notifyStateChanged()
    }
}
