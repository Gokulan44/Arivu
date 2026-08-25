package com.arivux.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen() {
    val manager = remember { SettingsManager() }
    
    var soundEffects by remember { mutableStateOf(manager.soundEffectsEnabled) }
    var notificationReminders by remember { mutableStateOf(manager.notificationReminders) }
    var privacyMode by remember { mutableStateOf(manager.privacyMode) }
    var dailyGoalXP by remember { mutableStateOf(manager.dailyGoalXP.toFloat()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Settings", fontSize = 22.sp, color = Color(0xFF1E293B))

        // Preference Settings Cards
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                
                // Sound Effects Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Sound Effects", fontSize = 15.sp, color = Color(0xFF1E293B))
                        Text("Play audio rewards on milestone unlocks.", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                    Switch(
                        checked = soundEffects,
                        onCheckedChange = {
                            soundEffects = it
                            manager.soundEffectsEnabled = it
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF4F46E5))
                    )
                }
                
                Divider()

                // Notification Reminders Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Push Reminders", fontSize = 15.sp, color = Color(0xFF1E293B))
                        Text("Remind me to maintain daily activity streaks.", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                    Switch(
                        checked = notificationReminders,
                        onCheckedChange = {
                            notificationReminders = it
                            manager.notificationReminders = it
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF4F46E5))
                    )
                }
                
                Divider()

                // Privacy Mode Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Private Profile", fontSize = 15.sp, color = Color(0xFF1E293B))
                        Text("Hide my username from public school leaderboards.", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                    Switch(
                        checked = privacyMode,
                        onCheckedChange = {
                            privacyMode = it
                            manager.privacyMode = it
                        },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF4F46E5))
                    )
                }
            }
        }

        // Daily XP Goal Configuration
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Daily XP Target Goal", fontSize = 15.sp, color = Color(0xFF1E293B))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Selected: ${dailyGoalXP.toInt()} XP", fontSize = 12.sp, color = Color(0xFF4F46E5))
                }
                Slider(
                    value = dailyGoalXP,
                    onValueChange = {
                        dailyGoalXP = it
                        manager.dailyGoalXP = it.toInt()
                    },
                    valueRange = 50f..250f,
                    steps = 3,
                    colors = SliderDefaults.colors(thumbColor = Color(0xFF4F46E5), activeTrackColor = Color(0xFF4F46E5))
                )
            }
        }
    }
}
