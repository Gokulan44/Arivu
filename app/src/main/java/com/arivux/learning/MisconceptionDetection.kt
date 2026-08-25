package com.arivux.learning

data class Misconception(
    val code: String,
    val title: String,
    val remediationAdvice: String
)

class MisconceptionDetection {
    private val activeMisconceptions = mutableSetOf<Misconception>()

    fun getDetected(): List<Misconception> {
        return activeMisconceptions.toList()
    }

    fun analyzeResistorMistake(studentEquivalentValue: Float, r1: Float, r2: Float): Misconception? {
        val seriesValue = r1 + r2
        val parallelValue = (r1 * r2) / (r1 + r2)

        // User calculated parallel formula for series circuit
        if (kotlin.math.abs(studentEquivalentValue - parallelValue) < 0.1f) {
            val mis = Misconception(
                "resistor_parallel_swap",
                "Parallel Formula Swap",
                "You calculated series resistance using 1/(1/R1 + 1/R2). In series, simply sum: R_eq = R1 + R2."
            )
            activeMisconceptions.add(mis)
            return mis
        }
        return null
    }

    fun analyzeArduinoCircuit(hasResistorLimit: Boolean): Misconception? {
        if (!hasResistorLimit) {
            val mis = Misconception(
                "led_short_circuit",
                "Short Circuit LED Danger",
                "An LED requires a series current-limiting resistor (e.g., 220 Ohm) to protect it from excessive current."
            )
            activeMisconceptions.add(mis)
            return mis
        }
        return null
    }

    fun clearMisconception(code: String) {
        activeMisconceptions.removeAll { it.code == code }
    }
}
