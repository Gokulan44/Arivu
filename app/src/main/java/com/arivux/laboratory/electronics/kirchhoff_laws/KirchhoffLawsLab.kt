package com.arivux.laboratory.electronics.kirchhoff_laws

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class KirchhoffLawsLab {
    val state = KirchhoffState()
    val engine = KirchhoffEngine(state)
    val validator = KirchhoffValidator(state)
    val measurements = KirchhoffMeasurements(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val battery1 = KirchhoffBatteryComponent("battery_1", Vector2D(100f, 150f), 6.0f)
        val battery2 = KirchhoffBatteryComponent("battery_2", Vector2D(500f, 150f), 9.0f)
        
        val resistor1 = KirchhoffResistorComponent("resistor_1", Vector2D(100f, 300f), 100.0f)
        val resistor2 = KirchhoffResistorComponent("resistor_2", Vector2D(500f, 300f), 200.0f)
        val resistor3 = KirchhoffResistorComponent("resistor_3", Vector2D(300f, 400f), 150.0f)

        val voltmeter = KirchhoffVoltmeterComponent("voltmeter_primary", Vector2D(300f, 550f))
        val ammeter = KirchhoffAmmeterComponent("ammeter_primary", Vector2D(300f, 10f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(battery1)
        stateManager.addObject(battery2)
        stateManager.addObject(resistor1)
        stateManager.addObject(resistor2)
        stateManager.addObject(resistor3)
        stateManager.addObject(voltmeter)
        stateManager.addObject(ammeter)

        // Configure initial values
        state.batteryVoltageV1 = 6.0f
        state.batteryVoltageV2 = 9.0f
        state.resistanceR1 = 100.0f
        state.resistanceR2 = 200.0f
        state.resistanceR3 = 150.0f

        labEngine.notifyStateChanged()
    }
}
