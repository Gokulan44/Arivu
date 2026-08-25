package com.arivux.laboratory.interaction

import com.arivux.laboratory.physics.Vector2D

interface DraggableObject {
    val id: String
    var position: Vector2D
    val width: Float
    val height: Float
    var isDragging: Boolean
    
    fun containsPoint(point: Vector2D): Boolean {
        return point.x >= position.x && point.x <= position.x + width &&
               point.y >= position.y && point.y <= position.y + height
    }

    fun onDragStart() {
        isDragging = true
    }

    fun onDrag(delta: Vector2D) {
        position = position + delta
    }

    fun onDragEnd() {
        isDragging = false
    }
}
