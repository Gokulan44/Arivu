package com.arivux.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LearningPath(
    nodes: List<LearningNode>,
    onNodeClick: (LearningNode) -> Unit
) {
    // Keep track of node center coordinates to draw connections in Canvas
    val nodePositions = remember { mutableStateMapOf<Int, Offset>() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Background connection lines Canvas
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val path = Path()
            val sortedNodes = nodes.sortedBy { it.id }
            
            for (i in 0 until sortedNodes.size - 1) {
                val startOffset = nodePositions[sortedNodes[i].id] ?: continue
                val endOffset = nodePositions[sortedNodes[i + 1].id] ?: continue
                
                path.reset()
                path.moveTo(startOffset.x, startOffset.y)
                
                // Curved control points for smooth serpentine trail
                val midX = (startOffset.x + endOffset.x) / 2
                val midY = (startOffset.y + endOffset.y) / 2
                
                if (startOffset.y == endOffset.y) {
                    // Straight horizontal connection
                    path.lineTo(endOffset.x, endOffset.y)
                } else {
                    // Curved serpentine S-path connection
                    path.cubicTo(
                        midX, startOffset.y,
                        midX, endOffset.y,
                        endOffset.x, endOffset.y
                    )
                }

                drawPath(
                    path = path,
                    color = Color(0xFF818CF8),
                    style = Stroke(
                        width = 4f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                    )
                )
            }
        }

        // Nodes Grid (Serpentine layout: row 1: 1->2, row 2: 4<-3, row 3: 5->6, row 4: 8<-7)
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val chunked = nodes.sortedBy { it.id }.chunked(2)
            chunked.forEachIndexed { rowIndex, rowNodes ->
                val orderedRowNodes = if (rowIndex % 2 != 0) rowNodes.reversed() else rowNodes

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    orderedRowNodes.forEach { node ->
                        LearningNodeCard(
                            node = node,
                            modifier = Modifier
                                .onGloballyPositioned { layoutCoordinates ->
                                    val size = layoutCoordinates.size
                                    val position = layoutCoordinates.positionInParent()
                                    // Store the center coordinates of the node relative to parent box
                                    nodePositions[node.id] = Offset(
                                        x = position.x + size.width / 2f,
                                        y = position.y + size.height / 2f
                                    )
                                }
                                .clickable { if (!node.isLocked) onNodeClick(node) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LearningNodeCard(
    node: LearningNode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(130.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = if (node.isLocked) Color(0xFFF1F5F9) else Color.White
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Circle domain index
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(if (node.isLocked) Color(0xFFCBD5E1) else Color(0xFF4F46E5)),
                contentAlignment = Alignment.Center
            ) {
                if (node.isLocked) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text = node.id.toString(),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                text = node.title,
                fontSize = 14.sp,
                color = if (node.isLocked) Color(0xFF64748B) else Color(0xFF1E293B)
            )

            Text(
                text = node.description,
                fontSize = 10.sp,
                color = Color(0xFF94A3B8)
            )

            // Progress star rating (e.g. 4/12)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFBBF24),
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${node.completedCount}/${node.totalCount}",
                    fontSize = 10.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}
