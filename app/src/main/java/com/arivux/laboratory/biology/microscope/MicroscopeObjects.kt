package com.arivux.laboratory.biology.microscope

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.DraggableObject
import com.arivux.laboratory.interaction.SliderObject

class SpecimenSlideComponent(id: String, position: Vector2D) : LabObject(id, position, 140f, 40f) {
    override val name = "Onion Cell Slide"
    override val type = "SpecimenSlide"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class MicroscopeComponent(id: String, position: Vector2D) : LabObject(id, position, 200f, 250f), SliderObject {
    override val name = "Lab Microscope"
    override val type = "Microscope"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    // Uses slider properties to adjust coarse focus
    override val minValue = 0f
    override val maxValue = 100f
    override var currentValue = 0f
}
