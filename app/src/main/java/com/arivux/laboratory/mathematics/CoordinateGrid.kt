package com.arivux.laboratory.mathematics

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D

class CoordinateGrid(
    id: String,
    position: Vector2D,
    width: Float = 400f,
    height: Float = 400f
) : LabObject(id, position, width, height) {
    override val name = "Cartesian Grid"
    override val type = "CoordinateGrid"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()

    var xMin = -10f
    var xMax = 10f
    var yMin = -10f
    var yMax = 10f
}
