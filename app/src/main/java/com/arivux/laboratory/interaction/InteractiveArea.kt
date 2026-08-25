package com.arivux.laboratory.interaction

import com.arivux.laboratory.physics.Vector2D

interface InteractiveArea {
    val id: String
    val position: Vector2D
    val width: Float
    val height: Float

    fun containsCentroid(obj: DraggableObject): Boolean {
        val centroidX = obj.position.x + obj.width / 2f
        val centroidY = obj.position.y + obj.height / 2f
        return centroidX >= position.x && centroidX <= position.x + width &&
               centroidY >= position.y && centroidY <= position.y + height
    }

    fun onObjectEntered(obj: DraggableObject)
    fun onObjectDropped(obj: DraggableObject)
    fun onObjectExited(obj: DraggableObject)
}
