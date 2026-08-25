package com.arivux.project

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StemProject(
    val title: String,
    val description: String,
    val difficulty: String,
    var status: String = "Available" // "Available", "Completed"
)

@Composable
fun ProjectScreen() {
    var activeSubmissionTitle by remember { mutableStateOf<String?>(null) }
    
    val projectsList = remember {
        mutableStateListOf(
            StemProject("Smart Greenhouse System", "Read soil moisture & temp on A0, output relay alerts.", "Intermediate"),
            StemProject("Arduino LED Traffic Light", "Sequence green, yellow, red LEDs on Pin 13, 12, 11.", "Beginner"),
            StemProject("Ultrasonic Radar Plotter", "Sweep servo motor, map distance scans to grids.", "Advanced")
        )
    }

    if (activeSubmissionTitle != null) {
        SubmissionScreen(
            projectTitle = activeSubmissionTitle!!,
            onBack = { activeSubmissionTitle = null }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "STEM Projects", fontSize = 22.sp, color = Color(0xFF1E293B))
            Text(
                text = "Apply your laboratory skills to build real-world Arduino projects. Connect repositories to evaluate.",
                fontSize = 12.sp,
                color = Color(0xFF64748B)
            )

            projectsList.forEach { project ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = 4.dp,
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(project.title, fontSize = 16.sp, color = Color(0xFF1E293B))
                            Text(
                                text = project.difficulty,
                                fontSize = 11.sp,
                                color = when (project.difficulty) {
                                    "Beginner" -> Color(0xFF10B981)
                                    "Intermediate" -> Color(0xFFF59E0B)
                                    else -> Color(0xFFEF4444)
                                }
                            )
                        }
                        Text(project.description, fontSize = 12.sp, color = Color(0xFF64748B))
                        
                        Button(
                            onClick = { activeSubmissionTitle = project.title },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4F46E5)),
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text("Submit Code", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
