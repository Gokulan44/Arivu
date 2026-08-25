package com.arivux.laboratory.electronics.arduino_led

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.electronics.arduino.*

class ArduinoLedEngine(
    val state: ArduinoLedState = ArduinoLedState()
) : DomainSolver {

    private var priorState = PinState.LOW

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val board = objects.firstOrNull { it is ArduinoBoard } as? ArduinoBoard ?: return
        val led = objects.firstOrNull { it is ArduinoLedComponent } as? ArduinoLedComponent

        if (led != null) {
            val anodeConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "D13" && it.toComponentId == led.id && it.toTerminalId == "anode") ||
                (it.toComponentId == board.id && it.toTerminalId == "D13" && it.fromComponentId == led.id && it.fromTerminalId == "anode")
            }
            val cathodeConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "GND" && it.toComponentId == led.id && it.toTerminalId == "cathode") ||
                (it.toComponentId == board.id && it.toTerminalId == "GND" && it.fromComponentId == led.id && it.fromTerminalId == "cathode")
            }

            state.isCircuitCorrect = anodeConnected && cathodeConnected
        } else {
            state.isCircuitCorrect = false
        }

        if (state.isCircuitCorrect && state.codeSketchSelected == "Blink") {
            val pin13 = board.pins["D13"]
            if (pin13 != null) {
                val currentState = pin13.digitalState
                if (currentState != priorState) {
                    if (currentState == PinState.HIGH) {
                        state.blinkCount++
                    }
                    priorState = currentState
                }
                state.isLedBlinking = state.blinkCount > 0
            }
        } else {
            state.isLedBlinking = false
            state.blinkCount = 0
        }

        state.blinkCompleted = state.blinkCount >= 3
    }
}
