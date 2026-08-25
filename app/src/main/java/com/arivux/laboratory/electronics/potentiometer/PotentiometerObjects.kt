package com.arivux.laboratory.electronics.potentiometer

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal
import com.arivux.laboratory.interaction.SliderObject

class PotentiometerBoardComponent(id: String, position: Vector2D) : LabObject(id, position, 600f, 160f), SliderObject {
    override val name = "Potentiometer Board (400cm)"
    override val type = "PotentiometerBoard"
    override val terminals = listOf(
        Terminal("left", Vector2D(10f, 50f)),
        Terminal("right", Vector2D(590f, 50f))
    )

    // Slider representing jockey placement along the 400cm wire loops
    override val minValue = 0f
    override val maxValue = 400f
    override var currentValue = 200f
}

class PrimaryBatteryComponent(id: String, position: Vector2D, val emf: Float) : LabObject(id, position, 140f, 80f) {
    override val name = "Primary Battery ($emf V)"
    override val type = "PrimaryBattery"
    override val terminals = listOf(
        Terminal("pos", Vector2D(0f, 40f)),
        Terminal("neg", Vector2D(140f, 40f))
    )
}

class SecondaryCellComponent(id: String, position: Vector2D, val emf: Float) : LabObject(id, position, 100f, 60f) {
    override val name = "Secondary Cell ($emf V)"
    override val type = "SecondaryCell"
    override val terminals = listOf(
        Terminal("pos", Vector2D(0f, 30f)),
        Terminal("neg", Vector2D(100f, 30f))
    )
}

class PotentiometerGalvanometerComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Galvanometer"
    override val type = "PotentiometerGalvanometer"
    override val terminals = listOf(
        Terminal("g_pos", Vector2D(10f, 50f)),
        Terminal("g_neg", Vector2D(90f, 50f))
    )
}
