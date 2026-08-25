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
    
    // Core Course States
    val activeCourse: String = "Physics",
    val coursesList: List<String> = listOf("Physics", "Chemistry", "Mathematics", "Electronics"),
    val activeUnitTitle: String = "Unit 2: Wave Optics & Oscillations",
    val activeUnitProgress: Float = 0.45f,
    
    // Daily Goal
    val dailyGoalXP: Int = 100,
    val dailyProgressXP: Int = 65,

    // Continue Learning
    val nextIncompleteLesson: String = "Simple Pendulum Lab",
    val nextIncompleteLessonDesc: String = "Oscillate the bob at 15 degrees and calculate the time period.",
    
    val learningTrail: List<LearningNode> = emptyList()
)

class HomeViewModel : ViewModel() {
    private val _dashboardState = MutableStateFlow(DashboardState())
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    init {
        selectCourse("Physics")
    }

    fun selectCourse(courseName: String) {
        val trail = getTrailForCourse(courseName)
        val unitTitle = when (courseName) {
            "Physics" -> "Unit 2: Wave Optics & Oscillations"
            "Chemistry" -> "Unit 1: Acids, Bases & Titrations"
            "Mathematics" -> "Unit 3: Cartesian Coordinate Systems"
            "Electronics" -> "Unit 4: Microcontroller & Circuits"
            else -> "Unit 1: Fundamentals"
        }
        val unitProgress = when (courseName) {
            "Physics" -> 0.45f
            "Chemistry" -> 0.25f
            "Mathematics" -> 0.60f
            "Electronics" -> 0.15f
            else -> 0f
        }
        val nextLesson = trail.firstOrNull { !it.isLocked && it.completedCount < it.totalCount }
            ?: trail.firstOrNull { !it.isLocked }
            ?: trail.firstOrNull()

        _dashboardState.value = _dashboardState.value.copy(
            activeCourse = courseName,
            learningTrail = trail,
            activeUnitTitle = unitTitle,
            activeUnitProgress = unitProgress,
            nextIncompleteLesson = nextLesson?.title ?: "Select Lesson",
            nextIncompleteLessonDesc = nextLesson?.description ?: "Begin your learning path."
        )
    }

    private fun getTrailForCourse(courseName: String): List<LearningNode> {
        return when (courseName) {
            "Physics" -> listOf(
                LearningNode(1, "Ohm's Law", "Verify V = I * R series loop", 12, 12, false),
                LearningNode(2, "Simple Pendulum", "Calculate period T = 2pi*sqrt(L/g)", 5, 12, false),
                LearningNode(3, "Convex Lens", "Optics bench focal lens checks", 0, 12, false),
                LearningNode(4, "Reflection", "Angle of incidence equal to reflection", 0, 12, true),
                LearningNode(5, "Refraction", "Determine Snell's index slab", 0, 12, true)
            )
            "Chemistry" -> listOf(
                LearningNode(1, "Acid-Base Titration", "Solve neutralization equivalents", 3, 12, false),
                LearningNode(2, "Solutions & pH", "Molar properties & reaction", 0, 12, true)
            )
            "Mathematics" -> listOf(
                LearningNode(1, "Coordinate Geometry", "Calculated distance & slopes", 8, 12, false),
                LearningNode(2, "Trigonometry Basics", "Solve angles & triangles", 0, 12, true)
            )
            "Electronics" -> listOf(
                LearningNode(1, "Arduino LED Blink", "GPIO Blink setup & loop", 12, 12, false),
                LearningNode(2, "Kirchhoff's Laws", "KVL & KCL loop equations", 0, 12, false),
                LearningNode(3, "Series & Parallel", "Equivalent resistance math", 0, 12, false),
                LearningNode(4, "Wheatstone Bridge", "Balance R1/R2 bridge loop", 0, 12, true),
                LearningNode(5, "Potentiometer", "Gradients balance lengths wire", 0, 12, true)
            )
            else -> emptyList()
        }
    }
}
