package com.arivux.laboratory.electronics.resistor_color_code

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.engine.InteractiveComponent

class ColorCodedResistorComponent(
    id: String,
    position: Vector2D,
    private val state: ResistorColorCodeState
) : LabObject(id, position, 200f, 80f), InteractiveComponent {

    override val name = "Interactive Resistor"
    override val type = "ColorCodedResistor"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    private val digitColors = listOf("Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Violet", "Gray", "White")
    private val multiplierColors = listOf("Black", "Brown", "Red", "Orange", "Yellow", "Green", "Blue", "Gold", "Silver")
    private val toleranceColors = listOf("Gold", "Silver", "Brown", "Red")

    override fun handleTap(relativePoint: Vector2D): Boolean {
        val x = relativePoint.x
        if (x < 50f) {
            val nextIndex = (digitColors.indexOf(state.currentBand1Color) + 1) % digitColors.size
            state.currentBand1Color = digitColors[nextIndex]
        } else if (x >= 50f && x < 100f) {
            val nextIndex = (digitColors.indexOf(state.currentBand2Color) + 1) % digitColors.size
            state.currentBand2Color = digitColors[nextIndex]
        } else if (x >= 100f && x < 150f) {
            val nextIndex = (multiplierColors.indexOf(state.currentBand3Color) + 1) % multiplierColors.size
            state.currentBand3Color = multiplierColors[nextIndex]
        } else {
            val nextIndex = (toleranceColors.indexOf(state.currentBand4Color) + 1) % toleranceColors.size
            state.currentBand4Color = toleranceColors[nextIndex]
        }
        return true
    }
}
