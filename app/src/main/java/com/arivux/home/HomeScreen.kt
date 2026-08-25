package com.arivux.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onNodeSelect: (LearningNode) -> Unit = {}
) {
    val state by viewModel.dashboardState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Welcome Header (Waving avatar greeting)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Welcome back, ${state.studentName}! 👋",
                    fontSize = 22.sp,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "Continue your STEM journey and unlock your potential.",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFFEEF2F6)),
                contentAlignment = Alignment.Center
            ) {
                Text("👦", fontSize = 24.sp)
            }
        }

        // 2. Level progression card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFDCFCE7)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⭐", fontSize = 28.sp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your Level: ${state.levelName}",
                        fontSize = 16.sp,
                        color = Color(0xFF1E293B)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = state.currentXP.toFloat() / state.nextLevelXP.toFloat(),
                        color = Color(0xFF22C55E),
                        backgroundColor = Color(0xFFE2E8F0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${state.currentXP} / ${state.nextLevelXP} XP",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }

        // 2.1 Daily Goal Card
        DailyGoalCard(
            goalXP = state.dailyGoalXP,
            progressXP = state.dailyProgressXP
        )

        // 2.2 Continue Learning Card
        ContinueLearning(
            lessonTitle = state.nextIncompleteLesson,
            lessonDesc = state.nextIncompleteLessonDesc,
            onStartClick = {
                // Mimics launching simulator
                val firstNode = state.learningTrail.firstOrNull { it.title == state.nextIncompleteLesson }
                if (firstNode != null) {
                    onNodeSelect(firstNode)
                }
            }
        )

        // 3. Learning path grid header
        Text(
            text = "Your Learning Path",
            fontSize = 18.sp,
            color = Color(0xFF1E293B),
            modifier = Modifier.padding(top = 8.dp)
        )

        // 3.1 Course Selector Horizontal List
        CourseSelector(
            courses = state.coursesList,
            selectedCourse = state.activeCourse,
            onCourseSelected = { viewModel.selectCourse(it) }
        )

        // 3.2 Active Unit Card
        UnitCard(
            unitTitle = state.activeUnitTitle,
            progress = state.activeUnitProgress
        )

        // Serpentine Learning Path Composable
        LearningPath(
            nodes = state.learningTrail,
            onNodeClick = onNodeSelect
        )

        // 4. Progress summary cards grid
        Text(
            text = "Your Progress Summary",
            fontSize = 18.sp,
            color = Color(0xFF1E293B)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProgressStatCard(
                label = "Lessons",
                value = "${state.lessonsCompleted}/${state.totalLessons}",
                icon = "📚",
                modifier = Modifier.weight(1f)
            )
            ProgressStatCard(
                label = "Projects",
                value = "${state.projectsBuilt}/${state.totalProjects}",
                icon = "🤖",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProgressStatCard(
                label = "Streak",
                value = "${state.streakDays} Days",
                icon = "🔥",
                modifier = Modifier.weight(1f)
            )
            ProgressStatCard(
                label = "Challenges",
                value = "${state.challengesCompleted}/${state.totalChallenges}",
                icon = "🏆",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ProgressStatCard(
    label: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(icon, fontSize = 24.sp)
            Column {
                Text(label, fontSize = 12.sp, color = Color(0xFF64748B))
                Text(value, fontSize = 16.sp, color = Color(0xFF1E293B))
            }
        }
    }
}
