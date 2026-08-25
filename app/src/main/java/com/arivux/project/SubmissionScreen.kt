package com.arivux.project

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun SubmissionScreen(
    projectTitle: String,
    onBack: () -> Unit
) {
    var descText by remember { mutableStateOf("") }
    var codeText by remember { mutableStateOf("void setup() {\n  pinMode(13, OUTPUT);\n}\n\nvoid loop() {\n  digitalWrite(13, HIGH);\n  delay(1000);\n  digitalWrite(13, LOW);\n  delay(1000);\n}") }
    var githubUser by remember { mutableStateOf("") }
    var githubToken by remember { mutableStateOf("") }
    
    val github = remember { GitHubIntegration() }
    val evaluator = remember { ProjectEvaluation() }
    val scrollState = rememberScrollState()

    var evaluationReport by remember { mutableStateOf<EvaluationReport?>(null) }
    var linkedRepo by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Submit Project: $projectTitle", fontSize = 20.sp, color = Color(0xFF1E293B))
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEEF2F6))
            ) {
                Text("Back", color = Color(0xFF475569))
            }
        }

        // 1. GitHub Link Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("1. Connect GitHub Repository", fontSize = 15.sp, color = Color(0xFF1E293B))
                
                if (!github.isConnected) {
                    TextField(
                        value = githubUser,
                        onValueChange = { githubUser = it },
                        label = { Text("GitHub Username") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = githubToken,
                        onValueChange = { githubToken = it },
                        label = { Text("Personal Access Token") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { github.connect(githubUser, githubToken) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4F46E5))
                    ) {
                        Text("Verify Account", color = Color.White)
                    }
                } else {
                    Text("Connected as: ${github.connectedUsername} ✅", color = Color(0xFF15803D))
                    val repos = github.getRepositories()
                    Text("Select Repository to Link:", fontSize = 12.sp, color = Color(0xFF64748B))
                    repos.forEach { repo ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { linkedRepo = repo.name }
                                .background(if (linkedRepo == repo.name) Color(0xFFEEF2F6) else Color.Transparent)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(repo.name, fontSize = 14.sp)
                            Text(if (repo.isPrivate) "Private" else "Public", fontSize = 11.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }

        // 2. Submission Details
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("2. Code & Description", fontSize = 15.sp, color = Color(0xFF1E293B))
                
                TextField(
                    value = descText,
                    onValueChange = { descText = it },
                    label = { Text("Project Summary & Explanation") },
                    modifier = Modifier.fillMaxWidth().height(100.dp)
                )
                
                TextField(
                    value = codeText,
                    onValueChange = { codeText = it },
                    label = { Text("Source Code (Arduino C++)") },
                    modifier = Modifier.fillMaxWidth().height(160.dp)
                )
            }
        }

        // Evaluate trigger
        Button(
            onClick = {
                evaluationReport = evaluator.evaluate(projectTitle, codeText, descText)
                if (linkedRepo != null && github.isConnected) {
                    val commit = github.pushFiles(linkedRepo!!, mapOf("code.ino" to codeText))
                    // Could append commit hash to logs
                }
            },
            enabled = linkedRepo != null,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF10B981))
        ) {
            Text("Evaluate & Push to GitHub 🚀", color = Color.White, fontSize = 16.sp)
        }

        // 3. Evaluation Reports
        evaluationReport?.let { report ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                backgroundColor = if (report.passed) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (report.passed) "Evaluation Passed! 🎉" else "Evaluation Failed. ⚠️",
                            fontSize = 18.sp,
                            color = if (report.passed) Color(0xFF15803D) else Color(0xFFB91C1C)
                        )
                        Text(
                            text = "Score: ${report.score}/100",
                            fontSize = 18.sp,
                            color = Color(0xFF1E293B)
                        )
                    }
                    Divider()
                    Text("Detailed Rubric Logs:", fontSize = 12.sp, color = Color(0xFF475569))
                    report.feedback.forEach { feed ->
                        Text("- $feed", fontSize = 12.sp, color = Color(0xFF1E293B))
                    }
                }
            }
        }
    }
}
