package com.arivux.laboratory.electronics.resistor_color_code

class ResistorColorCodeValidator(
    private val state: ResistorColorCodeState
) {
    fun checkColorCodeValidity(): Boolean {
        return state.isResistanceCorrect && state.isToleranceCorrect
    }
}
