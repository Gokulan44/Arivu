package com.arivux.laboratory.physics.concave_lens

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class ConcaveLensLab {
    val state = ConcaveLensState()
    val engine = ConcaveLensEngine(state)
    val validator = ConcaveLensValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val bench = ConcaveLensBenchComponent("optical_bench", Vector2D(50f, 300f))
        
        // Pixel coordinates: X_canvas = X_bench_start + Cm * 8f
        // Let's place:
        // Object at 10 cm => 50 + 10 * 8 = 130
        val light = ConcaveLightSourceComponent("light_source", Vector2D(130f, 200f))
        
        // Convex Lens at 40 cm => 50 + 40 * 8 = 370
        val convex = ConcaveConvexLensComponent("aux_convex", Vector2D(370f, 190f), 20.0f)
        
        // Concave Lens at 55 cm => 50 + 55 * 8 = 490
        val concave = ConcaveLensComponent("concave_lens", Vector2D(490f, 190f), -15.0f)
        
        // Screen at 85 cm => 50 + 85 * 8 = 730
        val screen = ConcaveScreenComponent("image_screen", Vector2D(730f, 190f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(bench)
        stateManager.addObject(light)
        stateManager.addObject(convex)
        stateManager.addObject(concave)
        stateManager.addObject(screen)

        // Configure initial values
        state.focalLengthF = -15.0f
        state.convexFocalLength = 20.0f
        state.objectPositionCm = 10f
        state.convexLensPositionCm = 40f
        state.concaveLensPositionCm = 55f
        state.screenPositionCm = 85f

        labEngine.notifyStateChanged()
    }
}
