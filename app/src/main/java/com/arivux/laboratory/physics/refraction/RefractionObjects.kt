package com.arivux.laboratory.physics.refraction

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.SliderObject

class GlassSlabComponent(id: String, position: Vector2D, val n: Float) : LabObject(id, position, 150f, 300f) {
    override val name = "Glass Slab (n=$n)"
    override val type = "GlassSlab"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class RefractionLaserComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 60f), SliderObject {
    override val name = "Laser Source"
    override val type = "RefractionLaser"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    // Slider controls the incident angle from 10 to 80 degrees
    override val minValue = 10f
    override val maxValue = 80f
    override var currentValue = 30f
}
