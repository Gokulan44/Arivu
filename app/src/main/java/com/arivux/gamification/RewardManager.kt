package com.arivux.gamification

data class Reward(
    val id: String,
    val description: String,
    val xpReward: Int,
    val shieldReward: Boolean = false
)

class RewardManager(
    private val xpManager: XPManager,
    private val streakManager: StreakManager
) {
    fun claimReward(reward: Reward): String {
        xpManager.addXP(reward.xpReward)
        if (reward.shieldReward) {
            streakManager.earnShield()
        }
        return "Claimed: ${reward.description}! (+${reward.xpReward} XP)"
    }

    fun getRewardForLesson(lessonId: String): Reward {
        return when (lessonId) {
            "ohms_law" -> Reward("ohms_law_rew", "Ohm's Law Completion", 150)
            "arduino_led" -> Reward("arduino_led_rew", "Arduino LED Blink Completion", 200)
            "microscope" -> Reward("microscope_rew", "Microscope Slide Focused", 100)
            else -> Reward("default_rew", "Lesson Completed", 50)
        }
    }
}
