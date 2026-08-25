package com.arivux.laboratory.electronics.led_circuit

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal

class LEDComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 100f) {
    override val name = "LED (Red)"
    override val type = "LED"
    
    override val terminals = listOf(
        Terminal("anode", Vector2D(10f, 50f)),
        Terminal("cathode", Vector2D(70f, 50f))
    )
}
