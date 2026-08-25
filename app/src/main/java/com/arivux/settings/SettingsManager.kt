package com.arivux.settings

class SettingsManager {
    var soundEffectsEnabled: Boolean = true
    var dailyGoalXP: Int = 100
    var privacyMode: Boolean = false
    var notificationReminders: Boolean = true

    fun updateSettings(sound: Boolean, goal: Int, privacy: Boolean, notify: Boolean) {
        soundEffectsEnabled = sound
        dailyGoalXP = goal
        privacyMode = privacy
        notificationReminders = notify
    }
}
