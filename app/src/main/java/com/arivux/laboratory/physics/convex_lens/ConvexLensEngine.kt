package com.arivux.laboratory.physics.convex_lens

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class ConvexLensEngine(
    val state: ConvexLensState = ConvexLensState()
) : DomainSolver {

    private val rayEngine = OpticalRayEngine()
    private val pixelScale = 8.0f // 8 pixels = 1 cm on bench scale

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val bench = objects.firstOrNull { it is OpticalBenchComponent } as? OpticalBenchComponent ?: return
        val obj = objects.firstOrNull { it is LightSourceComponent } as? LightSourceComponent ?: return
        val lens = objects.firstOrNull { it is ConvexLensComponent } as? ConvexLensComponent ?: return
        val screen = objects.firstOrNull { it is ScreenComponent } as? ScreenComponent ?: return

        // Convert canvas X coordinates to cm along the optical bench
        val benchStartX = bench.position.x
        state.objectPositionCm = (obj.position.x - benchStartX) / pixelScale
        state.lensPositionCm = (lens.position.x - benchStartX) / pixelScale
        state.screenPositionCm = (screen.position.x - benchStartX) / pixelScale

        val u = state.lensPositionCm - state.objectPositionCm
        val v = state.screenPositionCm - state.lensPositionCm
        state.objectDistanceU = u
        state.imageDistanceV = v

        if (u > state.focalLengthF) {
            // Lens Formula: 1/f = 1/v - 1/u => v_ideal = (f * u) / (u - f)
            val f = state.focalLengthF
            val vIdeal = (f * u) / (u - f)
            val focusError = kotlin.math.abs(v - vIdeal)

            // Blur diminishes as we approach optimal focus plane
            state.imageClarityPercent = (100f - (focusError * 15f)).coerceIn(0f, 100f)
            state.isFocused = state.imageClarityPercent > 95f
        } else {
            // u <= f implies virtual image: cannot form real focus on screen
            state.imageClarityPercent = 0f
            state.isFocused = false
        }

        // Trace rays on canvas pixels
        rayEngine.traceRays(
            objX = obj.position.x + obj.width / 2f,
            objY = obj.position.y + obj.height / 2f,
            lensX = lens.position.x + lens.width / 2f,
            lensY = lens.position.y + lens.height / 2f,
            screenX = screen.position.x + screen.width / 2f,
            screenY = screen.position.y + screen.height / 2f,
            focalLength = state.focalLengthF * pixelScale,
            state = state
        )
    }
}
