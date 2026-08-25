package com.arivux.laboratory.electronics.series_parallel

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal

class SeriesParallelResistorComponent(id: String, position: Vector2D, val nominalValue: Float) : LabObject(id, position, 120f, 60f) {
    override val name = "Resistor ($nominalValue Ω)"
    override val type = "SeriesParallelResistor"
    override val terminals = listOf(
        Terminal("a", Vector2D(0f, 30f)),
        Terminal("b", Vector2D(120f, 30f))
    )
}

class OhmmeterComponent(id: String, position: Vector2D) : LabObject(id, position, 120f, 100f) {
    override val name = "Ohmmeter"
    override val type = "Ohmmeter"
    override val terminals = listOf(
        Terminal("black", Vector2D(20f, 80f)),
        Terminal("red", Vector2D(100f, 80f))
    )
}
