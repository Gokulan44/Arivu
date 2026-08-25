package com.arivux.laboratory.physics.simple_pendulum

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D

class PendulumStandComponent(id: String, position: Vector2D) : LabObject(id, position, 60f, 250f) {
    override val name = "Pendulum Stand"
    override val type = "PendulumStand"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class PendulumBobComponent(id: String, position: Vector2D) : LabObject(id, position, 50f, 50f) {
    override val name = "Pendulum Bob (100g)"
    override val type = "PendulumBob"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}
