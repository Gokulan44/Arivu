package com.arivux.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DailyGoalCard(
    goalXP: Int,
    progressXP: Int,
    modifier: Modifier = Modifier
) {
    val completed = progressXP >= goalXP
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp,
        backgroundColor = if (completed) Color(0xFFDCFCE7) else Color(0xFFFFF7ED)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Daily XP Goal",
                    fontSize = 14.sp,
                    color = if (completed) Color(0xFF15803D) else Color(0xFFC2410C)
                )
                Text(
                    text = if (completed) "Daily target reached! 🎉" else "${goalXP - progressXP} XP to reach your daily goal!",
                    fontSize = 12.sp,
                    color = if (completed) Color(0xFF166534) else Color(0xFF9A3412)
                )
            }
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(if (completed) Color(0xFF22C55E) else Color(0xFFF97316)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$progressXP/$goalXP",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }
    }
}
