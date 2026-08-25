package com.arivux.laboratory.ohms_law

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal

class VoltmeterComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Voltmeter"
    override val type = "Voltmeter"
    
    override val terminals = listOf(
        Terminal("v_pos", Vector2D(10f, 50f)),
        Terminal("v_neg", Vector2D(90f, 50f))
    )
}

class AmmeterComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Ammeter"
    override val type = "Ammeter"
    
    override val terminals = listOf(
        Terminal("a_pos", Vector2D(10f, 50f)),
        Terminal("a_neg", Vector2D(90f, 50f))
    )
}
