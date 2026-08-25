package com.arivux.laboratory.physics.reflection

import kotlin.math.tan
import kotlin.math.PI

class RayTracingEngine {
    fun traceReflection(
        laserX: Float, laserY: Float,
        mirrorX: Float, mirrorY: Float,
        angleDegrees: Float,
        state: ReflectionState
    ) {
        // Normal to mirror is horizontal. Ray comes from laser on the left (laserX < mirrorX)
        state.rayStartX = laserX
        state.rayStartY = laserY

        // Hit point on mirror (x = mirrorX)
        state.rayHitX = mirrorX
        val angleRad = (angleDegrees * PI / 180.0).toFloat()
        
        // y_hit = y_start + delta_x * tan(angle)
        val dx = mirrorX - laserX
        state.rayHitY = laserY + dx * tan(angleRad)

        // Reflected ray goes backward (delta_x is negative)
        state.rayEndX = laserX
        state.rayEndY = state.rayHitY + dx * tan(angleRad)
    }
}
