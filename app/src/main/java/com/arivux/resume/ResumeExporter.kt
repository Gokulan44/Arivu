package com.arivux.resume

data class ResumeDetails(
    val name: String,
    val title: String,
    val email: String,
    val skills: List<String>,
    val achievements: List<String>,
    val projects: List<String>
)

class ResumeExporter {
    fun exportToText(details: ResumeDetails): String {
        return """
        ============================================================
        ${details.name.uppercase()} - ${details.title}
        ============================================================
        Contact: ${details.email} | Portfolio Verified via Arivu STEM
        
        TECHNICAL SKILLS:
        -----------------
        ${details.skills.joinToString(", ")}
        
        COMPLETED PROJECTS:
        -------------------
        ${details.projects.mapIndexed { idx, proj -> "${idx + 1}. $proj" }.joinToString("\n")}
        
        LABORATORY CREDENTIALS:
        -----------------------
        ${details.achievements.map { "- $it" }.joinToString("\n")}
        
        ============================================================
        Generated dynamically by Arivu Android Resume Engine.
        """.trimIndent()
    }
}
