package com.arivux.laboratory.electronics.multimeter_resistance

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal
import com.arivux.laboratory.interaction.SliderObject

class MultimeterResistorComponent(id: String, position: Vector2D, val nominalValue: Float) : LabObject(id, position, 120f, 60f) {
    override val name = "Resistor ($nominalValue Ω)"
    override val type = "MultimeterResistor"
    override val terminals = listOf(
        Terminal("a", Vector2D(0f, 30f)),
        Terminal("b", Vector2D(120f, 30f))
    )
}

class MultimeterComponent(id: String, position: Vector2D) : LabObject(id, position, 140f, 220f), SliderObject {
    override val name = "Digital Multimeter"
    override val type = "Multimeter"
    override val terminals = listOf(
        Terminal("black", Vector2D(40f, 200f)),
        Terminal("red", Vector2D(100f, 200f))
    )

    // Slider controls the dial position
    // Indices: 0 = 200, 1 = 2k, 2 = 20k, 3 = 200k
    override val minValue = 0f
    override val maxValue = 3f
    override var currentValue = 0f
}
