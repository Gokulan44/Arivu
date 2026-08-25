package com.arivux.laboratory.electronics.arduino_temperature

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.electronics.arduino.ArduinoBoard
import com.arivux.laboratory.electronics.arduino.ArduinoTemperatureSensorComponent
import com.arivux.laboratory.electronics.arduino.ArduinoLabEngine

class ArduinoTemperatureLab {
    val state = ArduinoTemperatureState()
    val engine = ArduinoTemperatureEngine(state)
    val validator = ArduinoTemperatureValidator(state)
    private val baseEngine = ArduinoLabEngine()

    fun initialize(labEngine: LabEngine) {
        // Register solvers
        labEngine.addSolver(baseEngine)
        labEngine.addSolver(engine)

        // Setup components
        val board = ArduinoBoard("arduino_uno", Vector2D(100f, 100f))
        val sensor = ArduinoTemperatureSensorComponent("temp_sensor_lm35", Vector2D(420f, 220f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(board)
        stateManager.addObject(sensor)

        // Configure initial values
        baseEngine.codeEngine.activeSketchName = "Temperature"
        state.codeSketchSelected = "Temperature"
        state.sensorValueC = 25f

        labEngine.notifyStateChanged()
    }
}
