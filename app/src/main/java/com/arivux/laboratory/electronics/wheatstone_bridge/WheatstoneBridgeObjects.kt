package com.arivux.laboratory.electronics.wheatstone_bridge

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal
import com.arivux.laboratory.interaction.SliderObject

class BridgeResistorComponent(id: String, position: Vector2D, val nominalValue: Float) : LabObject(id, position, 120f, 60f) {
    override val name = "Resistor ($nominalValue Ω)"
    override val type = "BridgeResistor"
    override val terminals = listOf(
        Terminal("a", Vector2D(0f, 30f)),
        Terminal("b", Vector2D(120f, 30f))
    )
}

class VariableResistorComponent(id: String, position: Vector2D) : LabObject(id, position, 140f, 80f), SliderObject {
    override val name = "Variable Resistor"
    override val type = "VariableResistor"
    override val terminals = listOf(
        Terminal("a", Vector2D(0f, 40f)),
        Terminal("b", Vector2D(140f, 40f))
    )

    // Slider controls the resistance from 50Ω to 250Ω
    override val minValue = 50f
    override val maxValue = 250f
    override var currentValue = 100f
}

class GalvanometerComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Galvanometer"
    override val type = "Galvanometer"
    override val terminals = listOf(
        Terminal("g_pos", Vector2D(10f, 50f)),
        Terminal("g_neg", Vector2D(90f, 50f))
    )
}
