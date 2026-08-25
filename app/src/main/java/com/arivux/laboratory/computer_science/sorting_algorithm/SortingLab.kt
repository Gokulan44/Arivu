package com.arivux.laboratory.computer_science.sorting_algorithm

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.computer_science.CSLabEngine

class SortingLab {
    val state = SortingState()
    val engine = SortingEngine(state)
    val csEngine = CSLabEngine()

    fun initialize(labEngine: LabEngine) {
        // 1. Register Computer Science and Sorting solvers to core engine
        labEngine.addSolver(csEngine)
        labEngine.addSolver(engine)

        // 2. Clear state and add visual array bars
        val initialValues = listOf(60, 20, 90, 40, 10, 70)
        state.reset(initialValues)

        val stateManager = labEngine.stateManager
        
        // Spawn 6 vertical array components spaced horizontally
        val startX = 200f
        val startY = 300f
        val spacing = 80f
        
        for (i in initialValues.indices) {
            val height = 100f + initialValues[i] * 2f // scaled height
            val element = ArrayElementComponent(
                id = "array_bar_$i",
                position = Vector2D(startX + i * spacing, startY),
                width = 50f,
                height = height,
                value = initialValues[i],
                index = i
            )
            stateManager.addObject(element)
        }

        labEngine.notifyStateChanged()
    }
}
