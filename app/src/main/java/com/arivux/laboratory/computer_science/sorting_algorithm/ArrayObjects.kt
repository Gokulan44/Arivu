package com.arivux.laboratory.computer_science.sorting_algorithm

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D

class ArrayElementComponent(
    id: String,
    position: Vector2D,
    width: Float = 50f,
    height: Float = 150f,
    val value: Int,
    val index: Int
) : LabObject(id, position, width, height) {
    override val name = "Element [$index]: $value"
    override val type = "ArrayElement"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}
