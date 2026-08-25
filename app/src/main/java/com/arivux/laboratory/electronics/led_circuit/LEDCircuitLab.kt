package com.arivux.laboratory.electronics.led_circuit

import com.arivux.laboratory.BatteryComponent
import com.arivux.laboratory.ResistorComponent
import com.arivux.laboratory.SwitchComponent
import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.electronics.CircuitLabEngine

class LEDCircuitLab {
    val state = LEDCircuitState()
    val engine = LEDCircuitEngine(state)
    val circEngine = CircuitLabEngine()

    fun initialize(labEngine: LabEngine) {
        // 1. Register Electronics and LED circuit solvers to core engine
        labEngine.addSolver(circEngine)
        labEngine.addSolver(engine)

        // 2. Add Battery, Resistor, LED, and Switch components
        val battery = BatteryComponent("battery_primary", Vector2D(100f, 150f))
        val switchKey = SwitchComponent("switch_primary", Vector2D(350f, 150f))
        val resistor = ResistorComponent("resistor_primary", Vector2D(220f, 350f))
        val led = LEDComponent("led_primary", Vector2D(500f, 350f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(battery)
        stateManager.addObject(switchKey)
        stateManager.addObject(resistor)
        stateManager.addObject(led)

        // 3. Configure initial state parameters
        state.batteryVoltage = 9.0f
        state.resistance = 330.0f // 330 ohms (safe value to limit current to ~21mA)

        labEngine.notifyStateChanged()
    }
}
