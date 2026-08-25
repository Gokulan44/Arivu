package com.arivux.laboratory.chemistry.acid_base_titration

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.PourableObject
import com.arivux.laboratory.interaction.FluidContent
import com.arivux.laboratory.interaction.SliderObject

class BeakerComponent(id: String, position: Vector2D) : LabObject(id, position, 120f, 120f), PourableObject {
    override val name = "Acid Beaker"
    override val type = "Beaker"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    override var maxCapacityMl = 250f
    override var currentFluid: FluidContent? = FluidContent("HCl (Acid)", 20f, "#D0E0FF", 0.1f)
    override var tiltAngleDegrees = 0f
}

class BuretteComponent(id: String, position: Vector2D) : LabObject(id, position, 60f, 300f), SliderObject {
    override val name = "NaOH Burette"
    override val type = "Burette"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    override val minValue = 0f
    override val maxValue = 3f // stopcock flow indices
    override var currentValue = 0f // 0 = closed
}
