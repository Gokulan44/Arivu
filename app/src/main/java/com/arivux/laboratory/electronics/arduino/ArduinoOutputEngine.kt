package com.arivux.laboratory.electronics.arduino

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal

class ArduinoLedComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 80f) {
    override val name = "LED"
    override val type = "ArduinoLED"
    override val terminals = listOf(
        Terminal("anode", Vector2D(20f, 60f)),
        Terminal("cathode", Vector2D(60f, 60f))
    )

    var isLit: Boolean = false
}

class ArduinoBuzzerComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 80f) {
    override val name = "Buzzer"
    override val type = "ArduinoBuzzer"
    override val terminals = listOf(
        Terminal("pos", Vector2D(20f, 60f)),
        Terminal("neg", Vector2D(60f, 60f))
    )

    var isSounding: Boolean = false
}

class ArduinoOutputEngine {
    fun updateOutputs(objects: List<LabObject>, board: ArduinoBoard, wires: List<com.arivux.laboratory.interaction.Wire>) {
        // 1. Update LED -> Pin D13 & GND
        val led = objects.firstOrNull { it is ArduinoLedComponent } as? ArduinoLedComponent
        if (led != null) {
            val anodeConnected = isPinConnectedToComponent(board.id, "D13", led.id, "anode", wires)
            val cathodeConnected = isPinConnectedToComponent(board.id, "GND", led.id, "cathode", wires)
            
            if (anodeConnected && cathodeConnected) {
                led.isLit = board.pins["D13"]?.digitalState == PinState.HIGH
            } else {
                led.isLit = false
            }
        }

        // 2. Update Buzzer -> Pin D8 & GND
        val buzzer = objects.firstOrNull { it is ArduinoBuzzerComponent } as? ArduinoBuzzerComponent
        if (buzzer != null) {
            val posConnected = isPinConnectedToComponent(board.id, "D8", buzzer.id, "pos", wires)
            val negConnected = isPinConnectedToComponent(board.id, "GND", buzzer.id, "neg", wires)

            if (posConnected && negConnected) {
                buzzer.isSounding = board.pins["D8"]?.digitalState == PinState.HIGH
            } else {
                buzzer.isSounding = false
            }
        }
    }

    private fun isPinConnectedToComponent(boardId: String, pinId: String, compId: String, termId: String, wires: List<com.arivux.laboratory.interaction.Wire>): Boolean {
        return wires.any {
            (it.fromComponentId == boardId && it.fromTerminalId == pinId && it.toComponentId == compId && it.toTerminalId == termId) ||
            (it.fromComponentId == compId && it.fromTerminalId == termId && it.toComponentId == boardId && it.toTerminalId == pinId)
        }
    }
}
