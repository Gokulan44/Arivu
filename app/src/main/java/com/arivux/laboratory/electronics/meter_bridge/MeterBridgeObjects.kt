package com.arivux.laboratory.electronics.meter_bridge

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal
import com.arivux.laboratory.interaction.SliderObject

class MeterBridgeResistorComponent(id: String, position: Vector2D, val nominalValue: Float) : LabObject(id, position, 120f, 60f) {
    override val name = "Resistor ($nominalValue Ω)"
    override val type = "MeterBridgeResistor"
    override val terminals = listOf(
        Terminal("a", Vector2D(0f, 30f)),
        Terminal("b", Vector2D(120f, 30f))
    )
}

class MeterBridgeBoardComponent(id: String, position: Vector2D) : LabObject(id, position, 600f, 100f), SliderObject {
    override val name = "Meter Bridge Board"
    override val type = "MeterBridgeBoard"
    override val terminals = listOf(
        Terminal("left", Vector2D(10f, 50f)),
        Terminal("right", Vector2D(590f, 50f))
    )

    // Slider representing jockey placement along 100cm wire
    override val minValue = 0f
    override val maxValue = 100f
    override var currentValue = 50f
}

class MeterBridgeGalvanometerComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Galvanometer"
    override val type = "MeterBridgeGalvanometer"
    override val terminals = listOf(
        Terminal("g_pos", Vector2D(10f, 50f)),
        Terminal("g_neg", Vector2D(90f, 50f))
    )
}
