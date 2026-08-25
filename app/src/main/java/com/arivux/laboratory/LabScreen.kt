package com.arivux.laboratory

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arivux.laboratory.engine.LabRenderer
import com.arivux.laboratory.engine.TouchAction
import com.arivux.laboratory.physics.Vector2D

@Composable
fun LabScreen(
    viewModel: LabViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val workspaceState by viewModel.workspaceState.collectAsState()
    val renderer = remember { LabRenderer() }
    
    var lastTouchPoint by remember { mutableStateOf(Offset.Zero) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FB))
    ) {
        // 1. Sidebar - Component Palette (STEMVOLT aesthetics)
        Column(
            modifier = Modifier
                .width(180.dp)
                .fillMaxHeight()
                .background(Color.White)
                .border(1.dp, Color(0xFFE2E8F0))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Components",
                fontSize = 18.sp,
                color = Color(0xFF1E293B),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val components = listOf("Battery", "Resistor", "Bulb", "Switch")
            for (comp in components) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clickable { viewModel.addComponent(comp, Vector2D(300f, 300f)) },
                    shape = RoundedCornerShape(10.dp),
                    backgroundColor = Color(0xFFEEF2F6),
                    elevation = 2.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = comp,
                            color = Color(0xFF4F46E5),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // 2. Main Workspace Canvas & Toolbars
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            // Interactive Laboratory Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                lastTouchPoint = offset
                                viewModel.handleTouchDown(offset.x, offset.y)
                            },
                            onDrag = { change, _ ->
                                lastTouchPoint = change.position
                                viewModel.handleTouchMove(change.position.x, change.position.y)
                            },
                            onDragEnd = {
                                viewModel.handleTouchUp(lastTouchPoint.x, lastTouchPoint.y)
                            }
                        )
                    }
            ) {
                // Perform drawing of components, wires, and coordinate grid
                renderer.drawWorkspace(
                    drawScope = this,
                    objects = viewModel.labEngine.stateManager.getObjects(),
                    wires = viewModel.labEngine.stateManager.getWires(),
                    activeAction = viewModel.labEngine.touchController.currentAction
                )
            }

            // Top Toolbar - Actions
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(30.dp))
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(30.dp))
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.undo() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEEF2F6)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Undo", color = Color(0xFF475569))
                }
                Button(
                    onClick = { viewModel.redo() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEEF2F6)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Redo", color = Color(0xFF475569))
                }
                Button(
                    onClick = { viewModel.clearWorkspace() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEF4444)),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Clear", color = Color.White)
                }
            }

            // Bottom-Right AI Tutor Mascot Card (STEMVOLT mascot hint bubble)
            Card(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
                    .width(280.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                backgroundColor = Color(0xFF4F46E5)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "STEMVOLT Assistant",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Connect the battery's positive terminal to the resistor to begin measuring Ohm's Law relationship!",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
