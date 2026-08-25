package com.arivux.laboratory.physics.reflection

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.SliderObject

class MirrorComponent(id: String, position: Vector2D) : LabObject(id, position, 20f, 300f) {
    override val name = "Plane Mirror"
    override val type = "Mirror"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class LaserSourceComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 60f), SliderObject {
    override val name = "Laser Source"
    override val type = "LaserSource"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    // Slider controls the incident ray angle from 10 to 80 degrees
    override val minValue = 10f
    override val maxValue = 80f
    override var currentValue = 30f
}
