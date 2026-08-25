package com.arivux.notifications

data class NotificationItem(
    val id: String,
    val title: String,
    val text: String,
    val timestamp: String,
    var isRead: Boolean = false
)

class NotificationManager {
    private val notificationsList = mutableListOf(
        NotificationItem("streak_alert", "Streak Alert! 🔥", "You have a 14-day streak active. Complete a lab today to extend it!", "10 mins ago"),
        NotificationItem("level_up", "Level Up! ⭐", "Congratulations! You reached Level 7: STEM Explorer.", "Yesterday"),
        NotificationItem("project_eval", "Project Grade Evaluated 📝", "Your project 'Smart Greenhouse System' was evaluated with score 90/100.", "2 days ago")
    )

    fun getNotifications(): List<NotificationItem> {
        return notificationsList
    }

    fun addNotification(title: String, text: String, time: String) {
        notificationsList.add(0, NotificationItem(
            id = java.util.UUID.randomUUID().toString(),
            title = title,
            text = text,
            timestamp = time
        ))
    }

    fun markAllAsRead() {
        notificationsList.forEach { it.isRead = true }
    }

    fun clearAll() {
        notificationsList.clear()
    }
}
