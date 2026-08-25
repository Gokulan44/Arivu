package com.arivux.laboratory.electronics.arduino_motion

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.electronics.arduino.*

class ArduinoMotionEngine(
    val state: ArduinoMotionState = ArduinoMotionState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val board = objects.firstOrNull { it is ArduinoBoard } as? ArduinoBoard ?: return
        val sensor = objects.firstOrNull { it is ArduinoMotionSensorComponent } as? ArduinoMotionSensorComponent
        val buzzer = objects.firstOrNull { it is ArduinoBuzzerComponent } as? ArduinoBuzzerComponent

        // Sensor connections check (VCC -> 5V, GND -> GND, OUT -> D2)
        if (sensor != null) {
            val vccConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "5V" && it.toComponentId == sensor.id && it.toTerminalId == "vcc") ||
                (it.toComponentId == board.id && it.toTerminalId == "5V" && it.fromComponentId == sensor.id && it.fromTerminalId == "vcc")
            }
            val gndConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "GND" && it.toComponentId == sensor.id && it.toTerminalId == "gnd") ||
                (it.toComponentId == board.id && it.toTerminalId == "GND" && it.fromComponentId == sensor.id && it.fromTerminalId == "gnd")
            }
            val outConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "D2" && it.toComponentId == sensor.id && it.toTerminalId == "out") ||
                (it.toComponentId == board.id && it.toTerminalId == "D2" && it.fromComponentId == sensor.id && it.fromTerminalId == "out")
            }
            state.isSensorConnected = vccConnected && gndConnected && outConnected
        } else {
            state.isSensorConnected = false
        }

        // Buzzer connections check (POS -> D8, NEG -> GND)
        if (buzzer != null) {
            val posConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "D8" && it.toComponentId == buzzer.id && it.toTerminalId == "pos") ||
                (it.toComponentId == board.id && it.toTerminalId == "D8" && it.fromComponentId == buzzer.id && it.fromTerminalId == "pos")
            }
            val negConnected = wires.any {
                (it.fromComponentId == board.id && it.fromTerminalId == "GND" && it.toComponentId == buzzer.id && it.toTerminalId == "neg") ||
                (it.toComponentId == board.id && it.toTerminalId == "GND" && it.fromComponentId == buzzer.id && it.fromTerminalId == "neg")
            }
            state.isBuzzerConnected = posConnected && negConnected
        } else {
            state.isBuzzerConnected = false
        }

        if (state.isSensorConnected && state.isBuzzerConnected && state.codeSketchSelected == "Motion") {
            state.motionDetected = sensor?.isOn ?: false
            state.buzzerBeeping = buzzer?.isSounding ?: false
            state.alertTriggered = state.motionDetected && state.buzzerBeeping
        } else {
            state.motionDetected = false
            state.buzzerBeeping = false
            state.alertTriggered = false
        }
    }
}
