package com.arivux.laboratory.physics.reflection

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class ReflectionEngine(
    val state: ReflectionState = ReflectionState()
) : DomainSolver {

    private val rayTracer = RayTracingEngine()

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val laser = objects.firstOrNull { it is LaserSourceComponent } as? LaserSourceComponent ?: return
        val mirror = objects.firstOrNull { it is MirrorComponent } as? MirrorComponent ?: return

        state.laserAngleDegrees = laser.currentValue

        // Law of Reflection: Angle of Incidence (i) = Angle of Reflection (r)
        state.incidentAngleDegrees = state.laserAngleDegrees
        state.reflectedAngleDegrees = state.incidentAngleDegrees
        state.isReflectionLawSatisfied = true

        rayTracer.traceReflection(
            laserX = laser.position.x + laser.width / 2f,
            laserY = laser.position.y + laser.height / 2f,
            mirrorX = mirror.position.x,
            mirrorY = mirror.position.y + mirror.height / 2f,
            angleDegrees = state.laserAngleDegrees,
            state = state
        )
    }
}
