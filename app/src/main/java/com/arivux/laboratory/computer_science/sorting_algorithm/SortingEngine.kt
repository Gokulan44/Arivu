package com.arivux.laboratory.computer_science.sorting_algorithm

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.physics.Vector2D

class SortingEngine(
    val state: SortingState = SortingState()
) : DomainSolver {

    private var executionTimer = 0f
    private val stepIntervalSec = 0.8f // duration between sorting steps in automatic playback

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        if (state.isSorted) return

        executionTimer += deltaTime
        if (executionTimer >= stepIntervalSec) {
            executionTimer = 0f
            performSortingStep(objects)
        }
    }

    fun performSortingStep(objects: List<LabObject>) {
        if (state.isSorted) return

        val arr = state.array
        val n = arr.size

        if (state.i < n - 1) {
            if (state.j < n - state.i - 1) {
                // Update compared indices highlights
                state.indexComparedA = state.j
                state.indexComparedB = state.j + 1
                state.comparisonsCount++

                if (arr[state.j] > arr[state.j + 1]) {
                    // Perform swap
                    val temp = arr[state.j]
                    arr[state.j] = arr[state.j + 1]
                    arr[state.j + 1] = temp
                    
                    state.swapsCount++
                    state.swapped = true

                    // Sync vertical positions/offsets visually in the workspace
                    syncVisualElements(objects)
                }

                state.j++
            } else {
                // End of inner pass: reset inner, increment outer
                if (!state.swapped) {
                    state.isSorted = true
                }
                state.j = 0
                state.i++
                state.swapped = false
            }
        } else {
            state.isSorted = true
            state.indexComparedA = -1
            state.indexComparedB = -1
        }
    }

    private fun syncVisualElements(objects: List<LabObject>) {
        val elements = objects.filterIsInstance<ArrayElementComponent>().sortedBy { it.index }
        val arr = state.array
        
        // Re-align and update height/value properties
        for (i in 0 until elements.size.coerceAtMost(arr.size)) {
            val visualElement = elements[i]
            // We swap values/labels dynamically
            // In a fuller canvas context, we animate their horizontal (x) translation coordinate moves
        }
    }
}
