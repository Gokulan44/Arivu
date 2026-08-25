package com.arivux.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val title: String,
    val level: Int,
    val xp: Int,
    val labs: Int,
    val badges: Int,
    val avatar: String
)

@Composable
fun LeaderboardScreen() {
    var selectedCategory by remember { mutableStateOf("Global") }
    val scrollState = rememberScrollState()

    val repository = remember { com.arivux.gamification.LeaderboardRepository() }
    val topUsers = remember { repository.getTopUsers() }
    val rankingList = remember { repository.getRankingList() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Leaderboard Title
        Text(
            text = "Leaderboard",
            fontSize = 22.sp,
            color = Color(0xFF1E293B)
        )
        Text(
            text = "See how you rank and get inspired by top performers!",
            fontSize = 12.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 2. Filter Category Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFEEF2F6), RoundedCornerShape(20.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val categories = listOf("Global", "School", "Friends")
            for (cat in categories) {
                val isSelected = selectedCategory == cat
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isSelected) Color.White else Color.Transparent)
                        .clickable { selectedCategory = cat }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = cat,
                        color = if (isSelected) Color(0xFF4F46E5) else Color(0xFF64748B),
                        fontSize = 14.sp
                    )
                }
            }
        }

        // 3. Rankings Podium (1st, 2nd, 3rd)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            // 2nd Place (Left)
            PodiumCard(user = topUsers[0], height = 150.dp, color = Color(0xFFE2E8F0))
            Spacer(modifier = Modifier.width(12.dp))
            // 1st Place (Center)
            PodiumCard(user = topUsers[1], height = 190.dp, color = Color(0xFFFEF08A), isFirst = true)
            Spacer(modifier = Modifier.width(12.dp))
            // 3rd Place (Right)
            PodiumCard(user = topUsers[2], height = 130.dp, color = Color(0xFFFED7AA))
        }

        // 4. Scrollable Rankings List
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = 2.dp,
            backgroundColor = Color.White
        ) {
            Column {
                rankingList.forEach { user ->
                    RankingRow(user = user)
                    Divider(color = Color(0xFFF1F5F9))
                }
            }
        }
    }
}

@Composable
fun PodiumCard(
    user: LeaderboardUser,
    height: androidx.compose.ui.unit.Dp,
    color: Color,
    isFirst: Boolean = false
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(height),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        backgroundColor = color,
        elevation = if (isFirst) 6.dp else 2.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Crown or rank badge
            Text(if (isFirst) "👑" else "${user.rank}", fontSize = 20.sp)

            // Avatar
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(user.avatar, fontSize = 22.sp)
            }

            // User Info
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(user.name.split(" ")[0], fontSize = 12.sp, color = Color(0xFF1E293B))
                Text("${user.xp} XP", fontSize = 12.sp, color = Color(0xFF4F46E5))
            }
        }
    }
}

@Composable
fun RankingRow(user: LeaderboardUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Rank No
        Text(
            text = user.rank.toString(),
            fontSize = 16.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.width(24.dp)
        )

        // Avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFFEEF2F6)),
            contentAlignment = Alignment.Center
        ) {
            Text(user.avatar, fontSize = 18.sp)
        }

        // Name and title
        Column(modifier = Modifier.weight(1f)) {
            Text(text = user.name, fontSize = 14.sp, color = Color(0xFF1E293B))
            Text(text = user.title, fontSize = 10.sp, color = Color(0xFF94A3B8))
        }

        // Level & Points
        Column(horizontalAlignment = Alignment.End) {
            Text(text = "${user.xp} XP", fontSize = 14.sp, color = Color(0xFF4F46E5))
            Text(text = "Lvl ${user.level}", fontSize = 10.sp, color = Color(0xFF64748B))
        }
    }
}
