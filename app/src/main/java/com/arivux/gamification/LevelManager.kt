package com.arivux.gamification

class LevelManager {
    fun getLevelTitle(level: Int): String {
        return when (level) {
            1 -> "STEM Novice"
            2 -> "Curious Mind"
            3 -> "Lab Assistant"
            4 -> "Science Apprentice"
            5 -> "Circuit Tinker"
            6 -> "Logic Wizard"
            7 -> "STEM Explorer"
            8 -> "Lab Master"
            else -> "STEM Champion"
        }
    }

    fun getXPRequiredForLevel(level: Int): Int {
        return level * 1000
    }
}
