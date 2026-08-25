package com.arivux.laboratory.physics

data class PhysicsObject(
    val id: String,
    var position: Vector2D,
    var velocity: Vector2D = Vector2D(0f, 0f),
    val mass: Float = 1.0f,
    val width: Float = 100f,
    val height: Float = 100f,
    val isStatic: Boolean = false,
    val useGravity: Boolean = false
) {
    fun getBounds(): Rect2D = Rect2D(position.x, position.y, position.x + width, position.y + height)
}

data class Rect2D(val left: Float, val top: Float, val right: Float, val bottom: Float) {
    fun intersects(other: Rect2D): Boolean {
        return left < other.right && right > other.left && top < other.bottom && bottom > other.top
    }
}

class PhysicsWorld(
    val gravity: Vector2D = Vector2D(0f, 9.8f),
    val bounds: Rect2D = Rect2D(0f, 0f, 1920f, 1080f)
) {
    private val activeObjects = mutableListOf<PhysicsObject>()

    fun addObject(obj: PhysicsObject) {
        activeObjects.add(obj)
    }

    fun removeObject(id: String) {
        activeObjects.removeAll { it.id == id }
    }

    fun clear() {
        activeObjects.clear()
    }

    fun getObjects(): List<PhysicsObject> = activeObjects

    fun update(deltaTime: Float) {
        for (obj in activeObjects) {
            if (obj.isStatic) continue

            // Apply gravity
            if (obj.useGravity) {
                obj.velocity = obj.velocity + (gravity * deltaTime)
            }

            // Apply position change
            val newPosition = obj.position + (obj.velocity * deltaTime)

            // Constraint check (boundaries)
            var finalX = newPosition.x
            var finalY = newPosition.y

            if (finalX < bounds.left) {
                finalX = bounds.left
                obj.velocity = Vector2D(-obj.velocity.x * 0.5f, obj.velocity.y)
            } else if (finalX + obj.width > bounds.right) {
                finalX = bounds.right - obj.width
                obj.velocity = Vector2D(-obj.velocity.x * 0.5f, obj.velocity.y)
            }

            if (finalY < bounds.top) {
                finalY = bounds.top
                obj.velocity = Vector2D(obj.velocity.x, -obj.velocity.y * 0.5f)
            } else if (finalY + obj.height > bounds.bottom) {
                finalY = bounds.bottom - obj.height
                obj.velocity = Vector2D(obj.velocity.x, -obj.velocity.y * 0.2f) // damp bounce
            }

            obj.position = Vector2D(finalX, finalY)
        }

        // Simple broad-phase collision check
        resolveCollisions()
    }

    private fun resolveCollisions() {
        for (i in 0 until activeObjects.size) {
            for (j in i + 1 until activeObjects.size) {
                val a = activeObjects[i]
                val b = activeObjects[j]

                val rectA = a.getBounds()
                val rectB = b.getBounds()

                if (rectA.intersects(rectB)) {
                    // Resolve overlap simply by pushing them apart
                    if (a.isStatic && b.isStatic) continue

                    val overlapX = (a.width + b.width) / 2 - kotlin.math.abs((a.position.x + a.width/2) - (b.position.x + b.width/2))
                    val overlapY = (a.height + b.height) / 2 - kotlin.math.abs((a.position.y + a.height/2) - (b.position.y + b.height/2))

                    if (overlapX < overlapY) {
                        // Push along X axis
                        val pushX = overlapX / 2f
                        if (!a.isStatic) a.position = Vector2D(a.position.x + (if (a.position.x < b.position.x) -pushX else pushX), a.position.y)
                        if (!b.isStatic) b.position = Vector2D(b.position.x + (if (b.position.x < a.position.x) -pushX else pushX), b.position.y)
                    } else {
                        // Push along Y axis
                        val pushY = overlapY / 2f
                        if (!a.isStatic) a.position = Vector2D(a.position.x, a.position.y + (if (a.position.y < b.position.y) -pushY else pushY))
                        if (!b.isStatic) b.position = Vector2D(b.position.x, b.position.y + (if (b.position.y < a.position.y) -pushY else pushY))
                    }
                }
            }
        }
    }
}
