package com.arivux.gamification

data class Badge(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    var isUnlocked: Boolean = false
)

class BadgeManager {
    private val badgesMap = mutableMapOf<String, Badge>().apply {
        put("ohm_solver", Badge("ohm_solver", "Ohm's Law Master", "Verified Ohm's Law circuit equations.", "⚡"))
        put("blink_creator", Badge("blink_creator", "Code Blinker", "Ran LED blink Arduino sketch.", "💡"))
        put("temp_logger", Badge("temp_logger", "Thermal Guard", "Logged temperature data points.", "🌡️"))
        put("motion_sentry", Badge("motion_sentry", "Security Sentry", "Triggered motion PIR buzzer alarm.", "🚨"))
        put("optics_focus", Badge("optics_focus", "Optics Scholar", "Focused a convex lens target image.", "🔍"))
        put("streak_week", Badge("streak_week", "7-Day Warrior", "Achieved a 7-day activity streak.", "🔥"))
    }

    fun unlockBadge(id: String): Boolean {
        val badge = badgesMap[id]
        if (badge != null && !badge.isUnlocked) {
            badge.isUnlocked = true
            return true
        }
        return false
    }

    fun getUnlockedBadges(): List<Badge> {
        return badgesMap.values.filter { it.isUnlocked }
    }

    fun getAllBadges(): List<Badge> {
        return badgesMap.values.toList()
    }
}
