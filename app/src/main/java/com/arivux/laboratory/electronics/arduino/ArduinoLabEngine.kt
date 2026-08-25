package com.arivux.laboratory.electronics.arduino

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class ArduinoLabEngine : DomainSolver {
    val codeEngine = ArduinoCodeEngine()
    private val sensorEngine = ArduinoSensorEngine()
    private val outputEngine = ArduinoOutputEngine()

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val board = objects.firstOrNull { it is ArduinoBoard } as? ArduinoBoard ?: return

        // 1. Solve Sensor values and write to Board input pins
        sensorEngine.updateSensors(objects, board, wires)

        // 2. Solve microcontroller setup() and loop() sketch ticks
        codeEngine.tick(board, deltaTime)

        // 3. Update Actuator outputs based on Board output pins
        outputEngine.updateOutputs(objects, board, wires)
    }
}
