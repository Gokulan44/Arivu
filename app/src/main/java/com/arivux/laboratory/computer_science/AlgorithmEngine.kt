package com.arivux.laboratory.computer_science

class AlgorithmEngine {
    var stepIndex = 0
    var isPlaying = false
    var executionSpeedMs = 500f

    fun reset() {
        stepIndex = 0
        isPlaying = false
    }

    fun stepForward() {
        stepIndex++
    }
}
