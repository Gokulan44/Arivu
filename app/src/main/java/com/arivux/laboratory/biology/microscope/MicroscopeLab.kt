package com.arivux.laboratory.biology.microscope

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.biology.BiologyLabEngine

class MicroscopeLab {
    val state = MicroscopeState()
    val engine = MicroscopeEngine(state)
    val bioEngine = BiologyLabEngine()

    fun initialize(labEngine: LabEngine) {
        // 1. Register Biology reaction and focus solvers to core engine
        labEngine.addSolver(bioEngine)
        labEngine.addSolver(engine)

        // 2. Add Microscope and Specimen Slide components
        val microscope = MicroscopeComponent("microscope_primary", Vector2D(300f, 200f))
        val slide = SpecimenSlideComponent("slide_onion", Vector2D(100f, 250f)) // Placed to the side for the user to drag

        val stateManager = labEngine.stateManager
        stateManager.addObject(microscope)
        stateManager.addObject(slide)

        // 3. Configure initial state parameters
        state.activeSpecimenId = "onion_skin_cells"
        state.stainApplied = "none" // requires staining to show details clearly

        labEngine.notifyStateChanged()
    }
}
