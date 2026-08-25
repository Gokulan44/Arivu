package com.arivux.learning

data class Recommendation(
    val title: String,
    val description: String,
    val labId: String,
    val priority: String // "High", "Medium", "Normal"
)

class RecommendationEngine {
    fun generateRecommendations(
        mastery: ConceptMastery,
        detection: MisconceptionDetection
    ): List<Recommendation> {
        val recommendations = mutableListOf<Recommendation>()
        val activeMisconceptions = detection.getDetected()

        // 1. High Priority based on misconceptions
        val hasShortCircuit = activeMisconceptions.any { it.code == "led_short_circuit" }
        if (hasShortCircuit) {
            recommendations.add(
                Recommendation(
                    title = "Review LED Blink Lab",
                    description = "Focus on the current-limiting resistor wiring in Series & Parallel connections.",
                    labId = "arduino_led",
                    priority = "High"
                )
            )
        }

        val hasParallelSwap = activeMisconceptions.any { it.code == "resistor_parallel_swap" }
        if (hasParallelSwap) {
            recommendations.add(
                Recommendation(
                    title = "Series & Parallel Resistors Lab",
                    description = "Verify and test resistor network equations step-by-step.",
                    labId = "series_parallel",
                    priority = "High"
                )
            )
        }

        // 2. Medium Priority based on mastery gaps
        if (mastery.getMastery("ray_optics") < 0.3f) {
            recommendations.add(
                Recommendation(
                    title = "Optics: Reflection Lab",
                    description = "Verify the angle of incidence equals reflection with light ray paths.",
                    labId = "reflection",
                    priority = "Medium"
                )
            )
        }

        if (mastery.getMastery("potentiometer") < 0.3f) {
            recommendations.add(
                Recommendation(
                    title = "Electronics: Potentiometer Lab",
                    description = "Calibrate voltage gradients and balance EMF loops.",
                    labId = "potentiometer",
                    priority = "Medium"
                )
            )
        }

        // Default recommendation if list empty
        if (recommendations.isEmpty()) {
            recommendations.add(
                Recommendation(
                    title = "Advance to Project Hub",
                    description = "Challenge yourself with the IoT Greenhouse Automation system.",
                    labId = "project_hub",
                    priority = "Normal"
                )
            )
        }

        return recommendations
    }
}
