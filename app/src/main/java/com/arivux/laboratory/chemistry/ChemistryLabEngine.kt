package com.arivux.laboratory.chemistry

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.interaction.PourableObject
import com.arivux.laboratory.interaction.InteractiveArea

class ChemistryLabEngine : DomainSolver {
    private val activeBurners = mutableListOf<LabObject>()
    
    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        // 1. Process pouring fluid dynamics
        processPouringTransitions(objects, deltaTime)

        // 2. Process thermal calculations (heating indicators)
        processHeatExchange(objects, deltaTime)
    }

    private fun processPouringTransitions(objects: List<LabObject>, deltaTime: Float) {
        val pourables = objects.filterIsInstance<PourableObject>()
        
        for (source in pourables) {
            if (source.isSpilling()) {
                // Find a nearby container to receive the fluid (simple proximity check)
                val sourceObj = source as LabObject
                val target = pourables.firstOrNull { dest ->
                    val destObj = dest as LabObject
                    destObj.id != sourceObj.id &&
                    sourceObj.position.distance(destObj.position) < 150f
                }

                if (target != null) {
                    val currentFluid = source.currentFluid
                    if (currentFluid != null) {
                        val pouredAmount = source.pour(deltaTime)
                        if (pouredAmount > 0f) {
                            target.receiveFluid(currentFluid, pouredAmount)
                        }
                    }
                }
            }
        }
    }

    private fun processHeatExchange(objects: List<LabObject>, deltaTime: Float) {
        // Basic template for burner heating plates
        // Can be extended during specific thermodynamics labs
    }
}
