package com.arivux.laboratory.electronics.resistor_color_code

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class ResistorColorCodeEngine(
    val state: ResistorColorCodeState = ResistorColorCodeState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val d1 = getDigit(state.currentBand1Color)
        val d2 = getDigit(state.currentBand2Color)
        val mult = getMultiplier(state.currentBand3Color)
        val tol = getTolerance(state.currentBand4Color)

        state.calculatedResistance = (d1 * 10f + d2) * mult
        state.calculatedTolerancePercent = tol

        state.isResistanceCorrect = kotlin.math.abs(state.calculatedResistance - state.targetResistance) < 1.0f
        state.isToleranceCorrect = kotlin.math.abs(state.calculatedTolerancePercent - state.targetTolerancePercent) < 0.1f
    }

    private fun getDigit(color: String): Int {
        return when (color) {
            "Black" -> 0
            "Brown" -> 1
            "Red" -> 2
            "Orange" -> 3
            "Yellow" -> 4
            "Green" -> 5
            "Blue" -> 6
            "Violet" -> 7
            "Gray" -> 8
            "White" -> 9
            else -> 0
        }
    }

    private fun getMultiplier(color: String): Float {
        return when (color) {
            "Black" -> 1f
            "Brown" -> 10f
            "Red" -> 100f
            "Orange" -> 1000f
            "Yellow" -> 10000f
            "Green" -> 100000f
            "Blue" -> 1000000f
            "Gold" -> 0.1f
            "Silver" -> 0.01f
            else -> 1f
        }
    }

    private fun getTolerance(color: String): Float {
        return when (color) {
            "Brown" -> 1f
            "Red" -> 2f
            "Gold" -> 5f
            "Silver" -> 10f
            else -> 20f
        }
    }
}
