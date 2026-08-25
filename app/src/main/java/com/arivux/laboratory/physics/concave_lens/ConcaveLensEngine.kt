package com.arivux.laboratory.physics.concave_lens

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class ConcaveLensEngine(
    val state: ConcaveLensState = ConcaveLensState()
) : DomainSolver {

    private val pixelScale = 8.0f

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val bench = objects.firstOrNull { it is ConcaveLensBenchComponent } as? ConcaveLensBenchComponent ?: return
        val obj = objects.firstOrNull { it is ConcaveLightSourceComponent } as? ConcaveLightSourceComponent ?: return
        val convexLens = objects.firstOrNull { it is ConcaveConvexLensComponent } as? ConcaveConvexLensComponent ?: return
        val concaveLens = objects.firstOrNull { it is ConcaveLensComponent } as? ConcaveLensComponent ?: return
        val screen = objects.firstOrNull { it is ConcaveScreenComponent } as? ConcaveScreenComponent ?: return

        val benchStartX = bench.position.x
        state.objectPositionCm = (obj.position.x - benchStartX) / pixelScale
        state.convexLensPositionCm = (convexLens.position.x - benchStartX) / pixelScale
        state.concaveLensPositionCm = (concaveLens.position.x - benchStartX) / pixelScale
        state.screenPositionCm = (screen.position.x - benchStartX) / pixelScale

        // Convex lens physics
        val uConvex = state.convexLensPositionCm - state.objectPositionCm
        val fConvex = state.convexFocalLength

        if (uConvex > fConvex) {
            val vConvexIdeal = (fConvex * uConvex) / (uConvex - fConvex)
            val focusI1 = state.convexLensPositionCm + vConvexIdeal

            // Concave lens is placed between convex lens and focus I1
            val uConcave = focusI1 - state.concaveLensPositionCm
            state.objectDistanceU = uConcave

            if (uConcave > 0f) {
                // Concave Lens Formula: 1/f = 1/v - 1/u => v = (f * u) / (u - f)
                val fConcave = state.focalLengthF // -15
                val vConcaveIdeal = (fConcave * uConcave) / (uConcave - fConcave)
                state.imageDistanceV = vConcaveIdeal

                val expectedScreenPos = state.concaveLensPositionCm + vConcaveIdeal
                val focusError = kotlin.math.abs(state.screenPositionCm - expectedScreenPos)

                state.imageClarityPercent = (100f - (focusError * 15f)).coerceIn(0f, 100f)
                state.isFocused = state.imageClarityPercent > 95f
            } else {
                state.imageClarityPercent = 0f
                state.isFocused = false
            }
        } else {
            state.imageClarityPercent = 0f
            state.isFocused = false
        }
    }
}
