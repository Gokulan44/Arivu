package com.arivux.project

data class EvaluationReport(
    val score: Int,
    val passed: Boolean,
    val feedback: List<String>
)

class ProjectEvaluation {
    fun evaluate(
        projectTitle: String,
        codeContent: String,
        description: String
    ): EvaluationReport {
        val feedback = mutableListOf<String>()
        var score = 0

        // 1. Structural compilation check
        if (codeContent.contains("void setup()") && codeContent.contains("void loop()")) {
            score += 40
            feedback.add("Compilation Check Passed: Standard Arduino structure detected.")
        } else {
            feedback.add("Compilation Check Failed: Missing setup() or loop() declarations.")
        }

        // 2. Project-specific requirements
        val isGreenhouse = projectTitle.contains("Greenhouse", ignoreCase = true)
        val isBlink = projectTitle.contains("Blink", ignoreCase = true) || projectTitle.contains("LED", ignoreCase = true)

        if (isGreenhouse) {
            if (codeContent.contains("A0") || codeContent.contains("analogRead")) {
                score += 30
                feedback.add("Requirement Passed: Successfully reading analog sensor on Pin A0.")
            } else {
                feedback.add("Requirement Failed: Greenhouse code should read sensor via analogRead().")
            }
        } else if (isBlink) {
            if (codeContent.contains("D13") || codeContent.contains("13")) {
                score += 30
                feedback.add("Requirement Passed: Pin 13 LED output mapped correctly.")
            } else {
                feedback.add("Requirement Failed: LED project should target Pin 13.")
            }
        } else {
            score += 15
            feedback.add("General Check: Generic sketch verified.")
        }

        // 3. Documentation quality
        if (description.length > 20) {
            score += 30
            feedback.add("Documentation Passed: Detailed description summary provided.")
        } else {
            feedback.add("Documentation Warn: Description is too brief. Provide more details next time.")
        }

        score = score.coerceIn(0, 100)
        return EvaluationReport(
            score = score,
            passed = score >= 70,
            feedback = feedback
        )
    }
}
