package com.arivux.laboratory.chemistry.acid_base_titration

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.chemistry.ChemistryLabEngine

class AcidBaseTitrationLab {
    val state = AcidBaseTitrationState()
    val engine = AcidBaseTitrationEngine(state)
    val chemEngine = ChemistryLabEngine()

    fun initialize(labEngine: LabEngine) {
        // 1. Register Chemistry reaction and pouring solvers to core engine
        labEngine.addSolver(chemEngine)
        labEngine.addSolver(engine)

        // 2. Add beaker and burette components (placed at appropriate coordinates)
        val beaker = BeakerComponent("beaker_titration", Vector2D(300f, 400f))
        val burette = BuretteComponent("burette_titration", Vector2D(330f, 80f)) // Aligned above the beaker

        val stateManager = labEngine.stateManager
        stateManager.addObject(beaker)
        stateManager.addObject(burette)

        // 3. Configure initial state parameters
        state.acidMolarity = 0.1f
        state.baseMolarity = 0.1f
        state.beakerSolution.reset()
        state.beakerSolution.addAcid(20.0f, state.acidMolarity) // 20mL HCl

        labEngine.notifyStateChanged()
    }
}
