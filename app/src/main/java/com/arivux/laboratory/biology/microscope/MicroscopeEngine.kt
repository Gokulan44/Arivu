package com.arivux.laboratory.biology.microscope

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.biology.StainEngine

class MicroscopeEngine(
    val state: MicroscopeState = MicroscopeState()
) : DomainSolver {

    private val stainEngine = StainEngine()
    private val optimalFocusValue = 50.0f

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val microscope = objects.firstOrNull { it is MicroscopeComponent } as? MicroscopeComponent ?: return
        val slide = objects.firstOrNull { it is SpecimenSlideComponent } as? SpecimenSlideComponent

        // 1. Check slide alignment coordinates on the stage area
        if (slide != null) {
            val dist = slide.position.distance(microscope.position + com.arivux.laboratory.physics.Vector2D(30f, 100f))
            state.isSlidePlaced = dist < 60f
        } else {
            state.isSlidePlaced = false
        }

        if (state.isSlidePlaced) {
            // Read focus adjustment from the microscope slider
            state.coarseFocusValue = microscope.currentValue
            
            // Calculate focal distance and focus error
            val focalDistance = state.coarseFocusValue + (state.fineFocusValue * 0.1f)
            val error = kotlin.math.abs(focalDistance - optimalFocusValue)

            // Blur reduces as we approach optimal focus
            val rawBlur = error * 0.5f
            state.blurRadius = rawBlur.coerceIn(0f, 25f)
            
            state.imageClarityPercent = (100f - (state.blurRadius * 4f)).coerceIn(0f, 100f)
            state.focusCompleted = state.imageClarityPercent > 90f
        } else {
            // Blur is maximum if no slide is on the stage
            state.blurRadius = 25f
            state.imageClarityPercent = 0f
            state.focusCompleted = false
        }

        // Apply contrast depending on stain status
        state.contrastFactor = stainEngine.applyStain(state.activeSpecimenId, state.stainApplied)
    }
}
