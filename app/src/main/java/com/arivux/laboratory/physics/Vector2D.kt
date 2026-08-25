package com.arivux.laboratory.physics

import kotlin.math.sqrt

data class Vector2D(val x: Float = 0f, val y: Float = 0f) {
    operator fun plus(other: Vector2D) = Vector2D(x + other.x, y + other.y)
    operator fun minus(other: Vector2D) = Vector2D(x - other.x, y - other.y)
    operator fun times(scalar: Float) = Vector2D(x * scalar, y * scalar)
    operator fun div(scalar: Float) = if (scalar != 0f) Vector2D(x / scalar, y / scalar) else Vector2D()

    fun length() = sqrt(x * x + y * y)
    
    fun distance(other: Vector2D): Float {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    fun normalize(): Vector2D {
        val len = length()
        return if (len > 0f) this / len else Vector2D()
    }

    fun dot(other: Vector2D) = x * other.x + y * other.y
}
