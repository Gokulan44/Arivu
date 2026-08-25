package com.arivux.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LearningNode(
    val id: Int,
    val title: String,
    val description: String,
    val completedCount: Int,
    val totalCount: Int,
    val isLocked: Boolean
)

data class DashboardState(
    val studentName: String = "Arun",
    val levelName: String = "Explorer",
    val currentXP: Int = 560,
    val nextLevelXP: Int = 1000,
    val lessonsCompleted: Int = 24,
    val totalLessons: Int = 80,
    val projectsBuilt: Int = 6,
    val totalProjects: Int = 20,
    val streakDays: Int = 14,
    val challengesCompleted: Int = 3,
    val totalChallenges: Int = 15,
    val learningTrail: List<LearningNode> = emptyList()
)

class HomeViewModel : ViewModel() {
    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        _dashboardState.value = DashboardState(
            studentName = "Arun",
            levelName = "Explorer",
            currentXP = 560,
            nextLevelXP = 1000,
            lessonsCompleted = 24,
            totalLessons = 80,
            projectsBuilt = 6,
            totalProjects = 20,
            streakDays = 14,
            challengesCompleted = 3,
            totalChallenges = 15,
            learningTrail = listOf(
                LearningNode(1, "Math", "Logic & Numbers", 5, 12, false),
                LearningNode(2, "Physics", "The Natural World", 4, 12, false),
                LearningNode(3, "Chemistry", "Matter & Reactions", 3, 12, false),
                LearningNode(4, "Biology", "Life & Living Things", 2, 12, false),
                LearningNode(5, "Technology", "Tools & Innovation", 3, 12, true),
                LearningNode(6, "Engineering", "Design & Build", 2, 12, true),
                LearningNode(7, "Coding", "Logic & Programming", 4, 12, true),
                LearningNode(8, "Innovation Lab", "Real World Projects", 0, 12, true)
            )
        )
    }
}
