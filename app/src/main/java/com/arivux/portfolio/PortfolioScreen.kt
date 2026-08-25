package com.arivux.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PortfolioScreen() {
    val manager = remember { PortfolioManager() }
    val summary = remember {
        PortfolioSummary(
            studentName = "Arun Kumar",
            totalXP = 1746,
            labsCompletedCount = 12,
            projectsCompletedCount = 3,
            badgesCount = 6,
            generatedAt = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(Date())
        )
    }

    var jsonOutput by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "My Portfolio", fontSize = 22.sp, color = Color(0xFF1E293B))

        // Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color(0xFF1E293B)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text("👦", fontSize = 36.sp)
                }
                Column {
                    Text(text = summary.studentName, fontSize = 20.sp, color = Color.White)
                    Text(text = "Verified Lab Portfolio", fontSize = 12.sp, color = Color(0xFF818CF8))
                }
            }
        }

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val stats = listOf(
                "Labs" to "${summary.labsCompletedCount}",
                "Projects" to "${summary.projectsCompletedCount}",
                "Badges" to "${summary.badgesCount}"
            )
            stats.forEach { (title, valStr) ->
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = valStr, fontSize = 20.sp, color = Color(0xFF4F46E5))
                        Text(text = title, fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                }
            }
        }

        // Badges Section
        Text(text = "Earned Badges", fontSize = 15.sp, color = Color(0xFF1E293B))
        
        val dummyBadges = listOf(
            "⚡ Ohm's Law Master",
            "💡 Code Blinker",
            "🌡️ Thermal Guard",
            "🚨 Security Sentry",
            "🔍 Optics Scholar",
            "🔥 7-Day Warrior"
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(dummyBadges) { badge ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = badge, fontSize = 12.sp, color = Color(0xFF334155))
                    }
                }
            }
        }

        Button(
            onClick = { jsonOutput = manager.generatePortfolioJson(summary) },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4F46E5)),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Export Portfolio Credentials JSON", color = Color.White)
        }

        jsonOutput?.let { json ->
            Card(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xFFF1F5F9)
            ) {
                Text(
                    text = json,
                    fontSize = 10.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    modifier = Modifier.padding(8.dp).verticalScroll(rememberScrollState())
                )
            }
        }
    }
}
