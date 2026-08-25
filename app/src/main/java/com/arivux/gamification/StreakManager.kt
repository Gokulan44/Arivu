package com.arivux.gamification

import java.util.Calendar
import java.util.Date

class StreakManager {
    var currentStreakDays: Int = 0
        private set

    var lastActiveDate: Date? = null
        private set

    var streakShieldsCount: Int = 2
        private set

    fun recordActivity(now: Date = Date()): StreakUpdateResult {
        val lastActive = lastActiveDate
        if (lastActive == null) {
            currentStreakDays = 1
            lastActiveDate = now
            return StreakUpdateResult.Started(1)
        }

        val lastCal = Calendar.getInstance().apply { time = lastActive }
        val nowCal = Calendar.getInstance().apply { time = now }

        // Clear time properties for day division comparisons
        lastCal.set(Calendar.HOUR_OF_DAY, 0)
        lastCal.set(Calendar.MINUTE, 0)
        lastCal.set(Calendar.SECOND, 0)
        lastCal.set(Calendar.MILLISECOND, 0)

        nowCal.set(Calendar.HOUR_OF_DAY, 0)
        nowCal.set(Calendar.MINUTE, 0)
        nowCal.set(Calendar.SECOND, 0)
        nowCal.set(Calendar.MILLISECOND, 0)

        val daysDiff = ((nowCal.timeInMillis - lastCal.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()

        return when {
            daysDiff == 0 -> {
                // Already active today
                StreakUpdateResult.NoChange(currentStreakDays)
            }
            daysDiff == 1 -> {
                // Streak continued!
                currentStreakDays++
                lastActiveDate = now
                StreakUpdateResult.Continued(currentStreakDays)
            }
            else -> {
                // Streak broken! Check shield status
                if (streakShieldsCount > 0) {
                    streakShieldsCount--
                    lastActiveDate = now
                    StreakUpdateResult.Shielded(currentStreakDays, streakShieldsCount)
                } else {
                    val oldStreak = currentStreakDays
                    currentStreakDays = 1
                    lastActiveDate = now
                    StreakUpdateResult.Broken(oldStreak)
                }
            }
        }
    }

    fun earnShield() {
        if (streakShieldsCount < 3) {
            streakShieldsCount++
        }
    }
}

sealed class StreakUpdateResult {
    data class Started(val currentStreak: Int) : StreakUpdateResult()
    data class Continued(val currentStreak: Int) : StreakUpdateResult()
    data class Shielded(val currentStreak: Int, val shieldsRemaining: Int) : StreakUpdateResult()
    data class Broken(val previousStreak: Int) : StreakUpdateResult()
    data class NoChange(val currentStreak: Int) : StreakUpdateResult()
}
