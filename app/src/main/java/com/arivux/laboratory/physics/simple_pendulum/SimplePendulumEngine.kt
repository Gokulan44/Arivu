package com.arivux.laboratory.physics.simple_pendulum

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.physics.Vector2D
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.sqrt
import kotlin.math.PI

class SimplePendulumEngine(
    val state: SimplePendulumState = SimplePendulumState()
) : DomainSolver {

    private var oscillationDirection = 1f
    private var priorAngle = 0f

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val stand = objects.firstOrNull { it is PendulumStandComponent } as? PendulumStandComponent ?: return
        val bob = objects.firstOrNull { it is PendulumBobComponent } as? PendulumBobComponent ?: return

        // Compute theoretical period: T = 2 * pi * sqrt(L/g)
        state.timePeriodT = (2f * PI.toFloat() * sqrt(state.lengthL / state.gravityG))

        val pivotPos = stand.position + Vector2D(30f, 20f) // pivot center

        if (bob.isDragging) {
            // Student is pulling the bob: calculate displacement angle manually
            val delta = bob.position - pivotPos
            state.angleRad = kotlin.math.atan2(delta.x, delta.y)
            state.angularVelocity = 0f
            state.isSwinging = false
            state.oscillationsCount = 0
            priorAngle = state.angleRad
        } else {
            // Pendulum is swinging under gravity: solve Euler-Cromer integration
            state.isSwinging = kotlin.math.abs(state.angleRad) > 0.01f || kotlin.math.abs(state.angularVelocity) > 0.01f

            if (state.isSwinging) {
                // alpha = -(g/L) * sin(theta)
                val alpha = -(state.gravityG / state.lengthL) * sin(state.angleRad)
                
                // velocity += alpha * dt
                state.angularVelocity += alpha * deltaTime
                
                // theta += velocity * dt
                state.angleRad += state.angularVelocity * deltaTime

                // Damping friction (air resistance)
                state.angularVelocity *= 0.998f 

                // Track oscillation cycles
                if (priorAngle < 0f && state.angleRad >= 0f && oscillationDirection > 0) {
                    state.oscillationsCount++
                    oscillationDirection = -1f
                } else if (priorAngle > 0f && state.angleRad <= 0f && oscillationDirection < 0) {
                    state.oscillationsCount++
                    oscillationDirection = 1f
                }
                priorAngle = state.angleRad

                // Translate angle coordinates to bob canvas pixels
                // Scaling: 1 meter = 150 pixels on canvas
                val lengthPixels = state.lengthL * 150f
                bob.position = pivotPos + Vector2D(
                    x = lengthPixels * sin(state.angleRad) - bob.width / 2f,
                    y = lengthPixels * cos(state.angleRad) - bob.height / 2f
                )
            } else {
                // Hang straight down
                val lengthPixels = state.lengthL * 150f
                bob.position = pivotPos + Vector2D(
                    x = -bob.width / 2f,
                    y = lengthPixels - bob.height / 2f
                )
            }
        }

        // Verify objectives
        state.lengthConfigured = kotlin.math.abs(state.lengthL - 1.5f) < 0.1f
        state.swingCompleted = state.oscillationsCount >= 10
    }
}
