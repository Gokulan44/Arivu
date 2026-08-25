package com.arivux.laboratory.electronics.arduino_temperature

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.electronics.arduino.*

class ArduinoTemperatureEngine(
    val state: ArduinoTemperatureState = ArduinoTemperatureState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val board = objects.firstOrNull { it is ArduinoBoard } as? ArduinoBoard ?: return
        val sensor = objects.firstOrNull { it is ArduinoTemperatureSensorComponent } as? ArduinoTemperatureSensorComponent

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
                (it.fromComponentId == board.id && it.fromTerminalId == "A0" && it.toComponentId == sensor.id && it.toTerminalId == "out") ||
                (it.toComponentId == board.id && it.toTerminalId == "A0" && it.fromComponentId == sensor.id && it.fromTerminalId == "out")
            }

            state.isSensorConnected = vccConnected && gndConnected && outConnected
        } else {
            state.isSensorConnected = false
        }

        if (state.isSensorConnected && state.codeSketchSelected == "Temperature") {
            state.sensorValueC = sensor?.currentValue ?: 0f
            if (state.sensorValueC > state.highestLogTempC) {
                state.highestLogTempC = state.sensorValueC
            }
            // Log completed if temperature raised above 40°C
            state.logCompleted = state.highestLogTempC >= 40.0f
        } else {
            state.sensorValueC = 0f
            state.highestLogTempC = 0f
            state.logCompleted = false
        }
    }
}
