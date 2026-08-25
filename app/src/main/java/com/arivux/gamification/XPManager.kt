package com.arivux.gamification

class XPManager(
    private val onLevelUp: (Int) -> Unit
) {
    var currentXP: Int = 0
        private set

    var currentLevel: Int = 1
        private set

    fun addXP(points: Int): Boolean {
        if (points <= 0) return false
        currentXP += points

        // Level threshold logic: each level requires level * 1000 XP
        val nextLevelThreshold = getXPForNextLevel(currentLevel)
        if (currentXP >= nextLevelThreshold) {
            currentLevel++
            onLevelUp(currentLevel)
            return true
        }
        return false
    }

    fun getXPForNextLevel(level: Int): Int {
        return level * 1000
    }

    fun getLevelProgressPercentage(): Float {
        val baseXP = if (currentLevel > 1) getXPForNextLevel(currentLevel - 1) else 0
        val targetXP = getXPForNextLevel(currentLevel)
        val needed = targetXP - baseXP
        val progress = currentXP - baseXP
        return (progress.toFloat() / needed.toFloat()).coerceIn(0f, 1f)
    }

    fun reset() {
        currentXP = 0
        currentLevel = 1
    }
}
