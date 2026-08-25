package com.arivux.laboratory.engine

import com.arivux.laboratory.interaction.DraggableObject
import com.arivux.laboratory.physics.Vector2D

class LabDragController(
    private val boundaryWidth: Float = 1920f,
    private val boundaryHeight: Float = 1080f,
    private val snapDistance: Float = 25f
) {
    private var draggedObject: DraggableObject? = null
    private var dragStartOffset = Vector2D(0f, 0f)

    fun startDragging(obj: DraggableObject, touchPoint: Vector2D) {
        draggedObject = obj
        dragStartOffset = touchPoint - obj.position
        obj.onDragStart()
    }

    fun drag(touchPoint: Vector2D, snapPoints: List<Vector2D> = emptyList()) {
        val obj = draggedObject ?: return
        
        // Calculate new raw position
        var targetPos = touchPoint - dragStartOffset

        // Apply boundary constraints
        val minX = 0f
        val minY = 0f
        val maxX = boundaryWidth - obj.width
        val maxY = boundaryHeight - obj.height
        
        var x = targetPos.x.coerceIn(minX, maxX)
        var y = targetPos.y.coerceIn(minY, maxY)

        // Snapping logic
        for (snapPoint in snapPoints) {
            val dist = Vector2D(x, y).distance(snapPoint)
            if (dist < snapDistance) {
                x = snapPoint.x
                y = snapPoint.y
                break
            }
        }

        // Apply movement
        val currentPos = obj.position
        val delta = Vector2D(x - currentPos.x, y - currentPos.y)
        obj.onDrag(delta)
    }

    fun stopDragging() {
        draggedObject?.onDragEnd()
        draggedObject = null
    }

    fun isDragging(): Boolean = draggedObject != null
    fun getDraggedObject(): DraggableObject? = draggedObject
}
