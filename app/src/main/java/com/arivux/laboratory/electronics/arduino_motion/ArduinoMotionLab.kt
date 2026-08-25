package com.arivux.laboratory.electronics.arduino_motion

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.electronics.arduino.ArduinoBoard
import com.arivux.laboratory.electronics.arduino.ArduinoMotionSensorComponent
import com.arivux.laboratory.electronics.arduino.ArduinoBuzzerComponent
import com.arivux.laboratory.electronics.arduino.ArduinoLabEngine

class ArduinoMotionLab {
    val state = ArduinoMotionState()
    val engine = ArduinoMotionEngine(state)
    val validator = ArduinoMotionValidator(state)
    private val baseEngine = ArduinoLabEngine()

    fun initialize(labEngine: LabEngine) {
        // Register solvers
        labEngine.addSolver(baseEngine)
        labEngine.addSolver(engine)

        // Setup components
        val board = ArduinoBoard("arduino_uno", Vector2D(100f, 100f))
        val sensor = ArduinoMotionSensorComponent("motion_sensor_pir", Vector2D(420f, 50f))
        val buzzer = ArduinoBuzzerComponent("buzzer_piezo", Vector2D(420f, 220f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(board)
        stateManager.addObject(sensor)
        stateManager.addObject(buzzer)

        // Configure initial values
        baseEngine.codeEngine.activeSketchName = "Motion"
        state.codeSketchSelected = "Motion"

        labEngine.notifyStateChanged()
    }
}
