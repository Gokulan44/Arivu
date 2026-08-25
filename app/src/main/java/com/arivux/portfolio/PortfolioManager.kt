package com.arivux.portfolio

data class PortfolioSummary(
    val studentName: String,
    val totalXP: Int,
    val labsCompletedCount: Int,
    val projectsCompletedCount: Int,
    val badgesCount: Int,
    val generatedAt: String
)

class PortfolioManager {
    fun generatePortfolioJson(summary: PortfolioSummary): String {
        return """
        {
          "portfolioOwner": "${summary.studentName}",
          "metrics": {
            "totalXP": ${summary.totalXP},
            "labsCompleted": ${summary.labsCompletedCount},
            "projectsCompleted": ${summary.projectsCompletedCount},
            "badgesEarned": ${summary.badgesCount}
          },
          "verificationUrl": "https://arivu.edu/portfolio/verify/${summary.studentName.lowercase().replace(" ", "-")}",
          "timestamp": "${summary.generatedAt}"
        }
        """.trimIndent()
    }
}
