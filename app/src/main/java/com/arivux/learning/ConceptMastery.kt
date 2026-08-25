package com.arivux.learning

data class Concept(
    val id: String,
    val name: String,
    var masteryScore: Float = 0.0f // 0.0 to 1.0
)

class ConceptMastery {
    private val conceptsMap = mutableMapOf<String, Concept>().apply {
        put("ohms_law", Concept("ohms_law", "Ohm's Law Relations"))
        put("resistors", Concept("resistors", "Resistor Networks"))
        put("potentiometer", Concept("potentiometer", "EMF Potentiometers"))
        put("ray_optics", Concept("ray_optics", "Ray Reflection & Refraction"))
        put("arduino_io", Concept("arduino_io", "Arduino GPIO Controls"))
    }

    fun updateMastery(conceptId: String, scoreDelta: Float) {
        val concept = conceptsMap[conceptId]
        if (concept != null) {
            concept.masteryScore = (concept.masteryScore + scoreDelta).coerceIn(0.0f, 1.0f)
        }
    }

    fun getMastery(conceptId: String): Float {
        return conceptsMap[conceptId]?.masteryScore ?: 0.0f
    }

    fun getAllConcepts(): List<Concept> {
        return conceptsMap.values.toList()
    }
}
