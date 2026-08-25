package com.arivux.laboratory.engine

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import android.graphics.Paint
import com.arivux.laboratory.physics.Vector2D

class LabRenderer {

    fun drawWorkspace(
        drawScope: DrawScope,
        objects: List<LabObject>,
        wires: List<Wire>,
        activeAction: TouchAction
    ) {
        with(drawScope) {
            // Draw grid background
            drawGrid(this)

            // 1. Draw Wires
            for (wire in wires) {
                val fromComp = objects.firstOrNull { it.id == wire.fromComponentId }
                val toComp = objects.firstOrNull { it.id == wire.toComponentId }
                
                if (fromComp != null && toComp != null) {
                    val fromPos = fromComp.getTerminalAbsolutePosition(wire.fromTerminalId)
                    val toPos = toComp.getTerminalAbsolutePosition(wire.toTerminalId)
                    
                    if (fromPos != null && toPos != null) {
                        drawWireCurve(this, fromPos, toPos, wire.colorHex, wire.currentFlow)
                    }
                }
            }

            // 2. Draw active wire connection (drag state)
            if (activeAction is TouchAction.ConnectingWire) {
                val fromComp = objects.firstOrNull { it.id == activeAction.fromComponentId }
                if (fromComp != null) {
                    val fromPos = fromComp.getTerminalAbsolutePosition(activeAction.fromTerminalId)
                    if (fromPos != null) {
                        drawWireCurve(this, fromPos, activeAction.currentTouchPoint, "#FF5722", 0f)
                    }
                }
            }

            // 3. Draw Lab Objects
            for (obj in objects) {
                drawComponent(this, obj)
            }
        }
    }

    private fun drawGrid(drawScope: DrawScope) {
        val width = drawScope.size.width
        val height = drawScope.size.height
        val step = 50f
        
        // Draw grid lines
        for (x in 0.. (width / step).toInt()) {
            drawScope.drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                start = Offset(x * step, 0f),
                end = Offset(x * step, height),
                strokeWidth = 1f
            )
        }
        for (y in 0.. (height / step).toInt()) {
            drawScope.drawLine(
                color = Color.LightGray.copy(alpha = 0.3f),
                start = Offset(0f, y * step),
                end = Offset(width, y * step),
                strokeWidth = 1f
            )
        }
    }

    private fun drawWireCurve(
        drawScope: DrawScope,
        from: Vector2D,
        to: Vector2D,
        colorHex: String,
        current: Float
    ) {
        val color = try {
            Color(android.graphics.Color.parseColor(colorHex))
        } catch (e: Exception) {
            Color.Blue
        }

        // Draw a smooth bezier curve between terminals
        val path = Path().apply {
            moveTo(from.x, from.y)
            // Control points for curvature
            val midY = (from.y + to.y) / 2
            cubicTo(from.x, midY, to.x, midY, to.x, to.y)
        }

        drawScope.drawPath(
            path = path,
            color = color,
            style = Stroke(width = 6f)
        )

        // Draw current pulse indicator if active current flow
        if (kotlin.math.abs(current) > 0.001f) {
            drawScope.drawCircle(
                color = Color.Yellow,
                radius = 5f,
                center = Offset((from.x + to.x) / 2, (from.y + to.y) / 2) // Simple midpoint estimation
            )
        }
    }

    private fun drawComponent(drawScope: DrawScope, obj: LabObject) {
        val pos = obj.position
        val paint = Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }

        // Draw main body box
        drawScope.drawRect(
            color = if (obj.isDragging) Color.Gray else Color.White,
            topLeft = Offset(pos.x, pos.y),
            size = androidx.compose.ui.geometry.Size(obj.width, obj.height),
            style = Stroke(width = 4f)
        )

        // Draw component title
        drawScope.drawContext.canvas.nativeCanvas.drawText(
            obj.name,
            pos.x + obj.width / 2,
            pos.y + obj.height / 2,
            paint
        )

        // Draw terminals (sockets)
        for (terminal in obj.terminals) {
            val termPos = terminal.getAbsolutePosition(pos)
            drawScope.drawCircle(
                color = Color.Red,
                radius = 8f,
                center = Offset(termPos.x, termPos.y)
            )
            drawScope.drawCircle(
                color = Color.Black,
                radius = 4f,
                center = Offset(termPos.x, termPos.y)
            )
        }
    }
}
