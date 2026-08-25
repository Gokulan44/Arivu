package com.arivux.laboratory.electronics.series_parallel

class SeriesParallelValidator(
    private val state: SeriesParallelState
) {
    fun verifySeriesEquivalent(): Boolean {
        if (state.connectionType != "series") return false
        val expected = state.resistanceR1 + state.resistanceR2
        val correct = kotlin.math.abs(state.measuredEquivalentResistance - expected) < 1.0f
        state.seriesVerified = correct
        return correct
    }

    fun verifyParallelEquivalent(): Boolean {
        if (state.connectionType != "parallel") return false
        val expected = (state.resistanceR1 * state.resistanceR2) / (state.resistanceR1 + state.resistanceR2)
        val correct = kotlin.math.abs(state.measuredEquivalentResistance - expected) < 1.0f
        state.parallelVerified = correct
        return correct
    }
}
