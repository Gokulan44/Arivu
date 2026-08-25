package com.arivux.laboratory.physics.refraction

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import kotlin.math.sin
import kotlin.math.asin
import kotlin.math.tan
import kotlin.math.PI

class RefractionEngine(
    val state: RefractionState = RefractionState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val laser = objects.firstOrNull { it is RefractionLaserComponent } as? RefractionLaserComponent ?: return
        val slab = objects.firstOrNull { it is GlassSlabComponent } as? GlassSlabComponent ?: return

        state.laserAngleDegrees = laser.currentValue
        state.incidentAngleDegrees = state.laserAngleDegrees

        val iRad = (state.incidentAngleDegrees * PI / 180.0).toFloat()
        val n = state.refractiveIndexN

        // Snell's Law: sin(i) = n * sin(r) => r = asin(sin(i) / n)
        val sinR = sin(iRad) / n
        val rRad = asin(sinR)
        state.refractedAngleDegrees = (rRad * 180.0 / PI).toFloat()

        // Ray tracing coordinates
        // Laser position
        val lx = laser.position.x + laser.width / 2f
        val ly = laser.position.y + laser.height / 2f

        state.rayStartX = lx
        state.rayStartY = ly

        // Entry point on slab (x = slab.position.x)
        val slabLeft = slab.position.x
        val slabRight = slab.position.x + slab.width

        state.rayEntryX = slabLeft
        val dx1 = slabLeft - lx
        state.rayEntryY = ly + dx1 * tan(iRad)

        // Exit point on slab (x = slab.position.x + slab.width)
        state.rayExitX = slabRight
        val dx2 = slab.width
        state.rayExitY = state.rayEntryY + dx2 * tan(rRad)

        // Emergent ray back in air (emerges at angle i)
        state.rayEndX = slabRight + 200f
        val dx3 = 200f
        state.rayEndY = state.rayExitY + dx3 * tan(iRad)

        // Verify Snell's Law
        val calculatedN = if (sin(rRad) > 0.001f) sin(iRad) / sin(rRad) else 0f
        state.isSnellsLawVerified = kotlin.math.abs(calculatedN - n) < 0.05f
    }
}
