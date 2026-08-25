package com.arivux.laboratory.physics.convex_lens

import com.arivux.laboratory.physics.Vector2D

class OpticalRayEngine {
    fun traceRays(
        objX: Float, objY: Float,
        lensX: Float, lensY: Float,
        screenX: Float, screenY: Float,
        focalLength: Float,
        state: ConvexLensState
    ) {
        val u = lensX - objX
        if (u <= 0f) return

        // Ray 1: Parallel to axis, then refracts through focus
        state.ray1StartX = objX
        state.ray1StartY = objY - 30f // object tip
        state.ray1LensX = lensX
        state.ray1LensY = lensY - 30f

        // Ray 2: Directly through center of the lens
        state.ray2StartX = objX
        state.ray2StartY = objY - 30f
        state.ray2LensX = lensX
        state.ray2LensY = lensY

        if (u > focalLength) {
            val vIdeal = (focalLength * u) / (u - focalLength)
            val imgX = lensX + vIdeal
            val magnification = -vIdeal / u
            val imgY = lensY + (30f * magnification)

            state.ray1EndX = imgX
            state.ray1EndY = imgY
            state.ray2EndX = imgX
            state.ray2EndY = imgY
        } else {
            // Virtual image: rays diverge
            state.ray1EndX = lensX + 150f
            state.ray1EndY = lensY - 30f + 50f
            state.ray2EndX = lensX + 150f
            state.ray2EndY = lensY + 50f
        }
    }
}
