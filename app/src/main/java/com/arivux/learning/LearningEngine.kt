package com.arivux.learning

import com.arivux.home.LearningNode

class LearningEngine {
    val mastery = ConceptMastery()
    val detector = MisconceptionDetection()
    private val recommender = RecommendationEngine()
    private val sequencer = AdaptiveSequencing()

    fun evaluateLabPerformance(
        conceptId: String,
        lastCompletedNodeId: Int,
        successScore: Float,
        currentNodes: List<LearningNode>,
        hasCircuitResistor: Boolean = true
    ): PerformanceReport {
        // 1. Update concept mastery score
        val delta = if (successScore >= 0.8f) 0.15f else -0.05f
        mastery.updateMastery(conceptId, delta)

        // 2. Perform misconception scans
        if (conceptId == "arduino_io" && !hasCircuitResistor) {
            detector.analyzeArduinoCircuit(hasResistorLimit = false)
        }

        // 3. Re-sequence learning nodes trail
        val updatedNodes = sequencer.sequenceNextNodes(currentNodes, lastCompletedNodeId, successScore)

        // 4. Generate next recommendations
        val recommendations = recommender.generateRecommendations(mastery, detector)

        return PerformanceReport(
            updatedNodes = updatedNodes,
            activeMisconceptions = detector.getDetected(),
            recommendations = recommendations
        )
    }
}

data class PerformanceReport(
    val updatedNodes: List<LearningNode>,
    val activeMisconceptions: List<Misconception>,
    val recommendations: List<Recommendation>
)
