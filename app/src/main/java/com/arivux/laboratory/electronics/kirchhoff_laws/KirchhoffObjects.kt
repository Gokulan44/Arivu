package com.arivux.laboratory.electronics.kirchhoff_laws

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal

class KirchhoffBatteryComponent(id: String, position: Vector2D, val defaultVoltage: Float) : LabObject(id, position, 140f, 80f) {
    override val name = "Battery ($defaultVoltage V)"
    override val type = "KirchhoffBattery"
    override val terminals = listOf(
        Terminal("pos", Vector2D(0f, 40f)),
        Terminal("neg", Vector2D(140f, 40f))
    )
}

class KirchhoffResistorComponent(id: String, position: Vector2D, val nominalValue: Float) : LabObject(id, position, 120f, 60f) {
    override val name = "Resistor ($nominalValue Ω)"
    override val type = "KirchhoffResistor"
    override val terminals = listOf(
        Terminal("a", Vector2D(0f, 30f)),
        Terminal("b", Vector2D(120f, 30f))
    )
}

class KirchhoffVoltmeterComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Voltmeter"
    override val type = "KirchhoffVoltmeter"
    override val terminals = listOf(
        Terminal("v_pos", Vector2D(10f, 50f)),
        Terminal("v_neg", Vector2D(90f, 50f))
    )
}

class KirchhoffAmmeterComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Ammeter"
    override val type = "KirchhoffAmmeter"
    override val terminals = listOf(
        Terminal("a_pos", Vector2D(10f, 50f)),
        Terminal("a_neg", Vector2D(90f, 50f))
    )
}
