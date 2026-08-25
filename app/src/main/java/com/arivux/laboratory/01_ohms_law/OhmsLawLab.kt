package com.arivux.laboratory.ohms_law

import com.arivux.laboratory.BatteryComponent
import com.arivux.laboratory.ResistorComponent
import com.arivux.laboratory.SwitchComponent
import com.arivux.laboratory.BulbComponent
import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class OhmsLawLab {
    val state = OhmsLawState()
    val engine = OhmsLawEngine(state)
    val validator = OhmsLawValidator(state)
    val measurements = OhmsLawMeasurements(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Ohm's Law Solver to core physics/simulation engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace (coordinates from layout guidelines)
        val battery = BatteryComponent("battery_primary", Vector2D(100f, 150f))
        val switchKey = SwitchComponent("switch_primary", Vector2D(350f, 150f))
        val resistor = ResistorComponent("resistor_primary", Vector2D(220f, 350f))
        val voltmeter = VoltmeterComponent("voltmeter_primary", Vector2D(220f, 500f))
        val ammeter = AmmeterComponent("ammeter_primary", Vector2D(500f, 350f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(battery)
        stateManager.addObject(switchKey)
        stateManager.addObject(resistor)
        stateManager.addObject(voltmeter)
        stateManager.addObject(ammeter)

        // Configure initial values
        state.batteryVoltage = 6.0f
        state.resistance = 100.0f // 100 ohms nominal

        labEngine.notifyStateChanged()
    }
}
