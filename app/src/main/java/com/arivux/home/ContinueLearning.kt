package com.arivux.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContinueLearning(
    lessonTitle: String,
    lessonDesc: String,
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = Color(0xFF1E293B) // Premium dark slab
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "CONTINUE LEARNING",
                fontSize = 11.sp,
                color = Color(0xFF818CF8) // indigo light
            )
            Text(
                text = lessonTitle,
                fontSize = 18.sp,
                color = Color.White
            )
            Text(
                text = lessonDesc,
                fontSize = 12.sp,
                color = Color(0xFF94A3B8)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4F46E5)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text(
                    text = "Enter Lab Simulator 🚀",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}
