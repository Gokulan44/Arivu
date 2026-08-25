package com.arivux.laboratory.engine

import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.physics.PhysicsWorld
import com.arivux.laboratory.physics.Vector2D
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface DomainSolver {
    fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float)
}

class LabEngine(
    val stateManager: LabStateManager = LabStateManager(),
    val physicsWorld: PhysicsWorld = PhysicsWorld()
) {
    val dragController = LabDragController()
    val touchController = LabTouchController(stateManager, dragController) {
        notifyStateChanged()
    }
    
    private val solvers = mutableListOf<DomainSolver>()
    
    private val _workspaceStateFlow = MutableStateFlow(stateManager.captureSnapshot())
    val workspaceStateFlow: StateFlow<LabWorkspaceState> = _workspaceStateFlow

    val simulationEngine = LabSimulationEngine { deltaTime ->
        tick(deltaTime)
    }

    fun addSolver(solver: DomainSolver) {
        solvers.add(solver)
    }

    fun startSimulation() {
        simulationEngine.start()
    }

    fun stopSimulation() {
        simulationEngine.stop()
    }

    private fun tick(deltaTime: Float) {
        // 1. Run physics world update
        physicsWorld.update(deltaTime)
        
        // Sync position back to LabObjects
        val physObjects = physicsWorld.getObjects()
        for (obj in stateManager.getObjects()) {
            val physObj = physObjects.firstOrNull { it.id == obj.id }
            if (physObj != null && !obj.isDragging) {
                obj.position = physObj.position
            }
        }

        // 2. Run domain solvers (e.g. circuits, chemistry)
        for (solver in solvers) {
            solver.solve(stateManager.getObjects(), stateManager.getWires(), deltaTime)
        }

        notifyStateChanged()
    }

    fun notifyStateChanged() {
        _workspaceStateFlow.value = stateManager.captureSnapshot()
    }

    fun destroy() {
        simulationEngine.destroy()
    }
}
