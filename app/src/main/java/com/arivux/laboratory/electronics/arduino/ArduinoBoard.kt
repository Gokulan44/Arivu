package com.arivux.laboratory.electronics.arduino

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal

class ArduinoBoard(id: String, position: Vector2D) : LabObject(id, position, 250f, 320f) {
    override val name = "Arduino Uno R3"
    override val type = "ArduinoBoard"

    // Terminals represent pins on the board
    override val terminals = listOf(
        // Digital Pins (right edge header)
        Terminal("D0", Vector2D(230f, 20f)),
        Terminal("D1", Vector2D(230f, 35f)),
        Terminal("D2", Vector2D(230f, 50f)),
        Terminal("D3", Vector2D(230f, 65f)),
        Terminal("D4", Vector2D(230f, 80f)),
        Terminal("D5", Vector2D(230f, 95f)),
        Terminal("D6", Vector2D(230f, 110f)),
        Terminal("D7", Vector2D(230f, 125f)),
        Terminal("D8", Vector2D(230f, 150f)),
        Terminal("D9", Vector2D(230f, 165f)),
        Terminal("D10", Vector2D(230f, 180f)),
        Terminal("D11", Vector2D(230f, 195f)),
        Terminal("D12", Vector2D(230f, 210f)),
        Terminal("D13", Vector2D(230f, 225f)),

        // Power & Analog Pins (left edge header)
        Terminal("GND", Vector2D(20f, 20f)),
        Terminal("5V", Vector2D(20f, 35f)),
        Terminal("A0", Vector2D(20f, 100f)),
        Terminal("A1", Vector2D(20f, 115f)),
        Terminal("A2", Vector2D(20f, 130f)),
        Terminal("A3", Vector2D(20f, 145f)),
        Terminal("A4", Vector2D(20f, 160f)),
        Terminal("A5", Vector2D(20f, 175f))
    )

    // Virtual pin map for simulator
    val pins = mutableMapOf<String, ArduinoPin>().apply {
        for (i in 0..13) {
            put("D$i", ArduinoPin("D$i", isDigital = true))
        }
        for (i in 0..5) {
            put("A$i", ArduinoPin("A$i", isDigital = false))
        }
    }
}
