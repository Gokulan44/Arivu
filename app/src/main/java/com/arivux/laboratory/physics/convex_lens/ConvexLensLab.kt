package com.arivux.laboratory.physics.convex_lens

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class ConvexLensLab {
    val state = ConvexLensState()
    val engine = ConvexLensEngine(state)
    val validator = ConvexLensValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val bench = OpticalBenchComponent("optical_bench", Vector2D(50f, 300f))
        
        // Pixel coordinates: X_canvas = X_bench_start + Cm * 8f
        // Let's place:
        // Object at 10 cm => 50 + 10 * 8 = 130
        val light = LightSourceComponent("light_source", Vector2D(130f, 200f))
        
        // Lens at 40 cm => 50 + 40 * 8 = 370
        val lens = ConvexLensComponent("convex_lens", Vector2D(370f, 190f), 20.0f)
        
        // Screen at 90 cm => 50 + 90 * 8 = 770
        val screen = ScreenComponent("image_screen", Vector2D(770f, 190f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(bench)
        stateManager.addObject(light)
        stateManager.addObject(lens)
        stateManager.addObject(screen)

        // Configure initial values
        state.focalLengthF = 20.0f
        state.objectPositionCm = 10f
        state.lensPositionCm = 40f
        state.screenPositionCm = 90f

        labEngine.notifyStateChanged()
    }
}
