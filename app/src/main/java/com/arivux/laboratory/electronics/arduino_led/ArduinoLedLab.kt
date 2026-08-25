package com.arivux.laboratory.electronics.arduino_led

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.electronics.arduino.ArduinoBoard
import com.arivux.laboratory.electronics.arduino.ArduinoLedComponent
import com.arivux.laboratory.electronics.arduino.ArduinoLabEngine

class ArduinoLedLab {
    val state = ArduinoLedState()
    val engine = ArduinoLedEngine(state)
    val validator = ArduinoLedValidator(state)
    private val baseEngine = ArduinoLabEngine()

    fun initialize(labEngine: LabEngine) {
        // Register solvers
        labEngine.addSolver(baseEngine)
        labEngine.addSolver(engine)

        // Setup components
        val board = ArduinoBoard("arduino_uno", Vector2D(100f, 100f))
        val led = ArduinoLedComponent("led_primary", Vector2D(420f, 220f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(board)
        stateManager.addObject(led)

        // Select blink code
        baseEngine.codeEngine.activeSketchName = "Blink"
        state.codeSketchSelected = "Blink"

        labEngine.notifyStateChanged()
    }
}
