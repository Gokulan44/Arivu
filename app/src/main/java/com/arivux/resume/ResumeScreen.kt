package com.arivux.resume

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResumeScreen() {
    val exporter = remember { ResumeExporter() }
    
    var name by remember { mutableStateOf("Arun Kumar") }
    var title by remember { mutableStateOf("STEM Explorer & Tinkerer") }
    var email by remember { mutableStateOf("arun.kumar@arivu.edu") }
    
    val skills = listOf("Arduino C++ Programming", "Mesh Loop Equations (KVL/KCL)", "Equivalent Circuit Solving", "Lens Equation Tracing")
    val projects = listOf("Smart Greenhouse Automation System", "Ultrasonic Radar Sweep Plotter", "LED Sequence Traffic Signal Node")
    val achievements = listOf("Simple Pendulum Lab Certified (Oscillation periods)", "Ohm's Law Lab Verified", "Wheatstone Bridge Neutralization Balanced")
    
    var resumeOutput by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "My Resume Builder", fontSize = 22.sp, color = Color(0xFF1E293B))

        // 1. Personal Profile Inputs
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("1. Personal Details", fontSize = 15.sp, color = Color(0xFF1E293B))
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Professional Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Contact Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // 2. Summary of Verified Skills
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("2. Verified Competencies", fontSize = 15.sp, color = Color(0xFF1E293B))
                skills.forEach { skill ->
                    Text("• $skill", fontSize = 12.sp, color = Color(0xFF475569))
                }
            }
        }

        Button(
            onClick = {
                val details = ResumeDetails(name, title, email, skills, achievements, projects)
                resumeOutput = exporter.exportToText(details)
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4F46E5)),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Compile & Export Resume", color = Color.White)
        }

        // Export Text View
        resumeOutput?.let { text ->
            Card(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xFF0F172A)
            ) {
                Text(
                    text = text,
                    fontSize = 11.sp,
                    color = Color(0xFFE2E8F0),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    modifier = Modifier.padding(12.dp).verticalScroll(rememberScrollState())
                )
            }
        }
    }
}
