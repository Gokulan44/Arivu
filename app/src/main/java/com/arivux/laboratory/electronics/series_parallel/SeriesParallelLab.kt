package com.arivux.laboratory.electronics.series_parallel

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D

class SeriesParallelLab {
    val state = SeriesParallelState()
    val engine = SeriesParallelEngine(state)
    val validator = SeriesParallelValidator(state)

    fun initialize(labEngine: LabEngine) {
        // 1. Register Solver to core engine
        labEngine.addSolver(engine)

        // 2. Setup initial components in the workspace
        val resistor1 = SeriesParallelResistorComponent("resistor_1", Vector2D(100f, 200f), 100.0f)
        val resistor2 = SeriesParallelResistorComponent("resistor_2", Vector2D(350f, 200f), 200.0f)
        val ohmmeter = OhmmeterComponent("ohmmeter_primary", Vector2D(220f, 400f))

        val stateManager = labEngine.stateManager
        stateManager.addObject(resistor1)
        stateManager.addObject(resistor2)
        stateManager.addObject(ohmmeter)

        // Configure initial values
        state.resistanceR1 = 100.0f
        state.resistanceR2 = 200.0f

        labEngine.notifyStateChanged()
    }
}
