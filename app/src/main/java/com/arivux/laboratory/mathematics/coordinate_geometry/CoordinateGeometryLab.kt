package com.arivux.laboratory.mathematics.coordinate_geometry

import com.arivux.laboratory.engine.LabEngine
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.mathematics.CoordinateGrid
import com.arivux.laboratory.engine.LabObject

class CoordinateGeometryLab {
    val state = CoordinateGeometryState()
    val engine = CoordinateGeometryEngine(state)
    val mathEngine = com.arivux.laboratory.mathematics.MathLabEngine()

    fun initialize(labEngine: LabEngine) {
        // 1. Register Mathematics and Geometry solvers to core engine
        labEngine.addSolver(mathEngine)
        labEngine.addSolver(engine)

        // 2. Add Coordinate Grid and two draggable point nodes
        val grid = CoordinateGrid("cartesian_grid", Vector2D(200f, 150f), 500f, 500f)
        val pointA = PointPlottedComponent("point_a", Vector2D(150f, 400f), "A")
        val pointB = PointPlottedComponent("point_b", Vector2D(600f, 250f), "B")

        val stateManager = labEngine.stateManager
        stateManager.addObject(grid)
        stateManager.addObject(pointA)
        stateManager.addObject(pointB)

        // 3. Configure initial state parameters
        state.ax = 0f
        state.ay = 0f
        state.bx = 5f
        state.by = 5f

        labEngine.notifyStateChanged()
    }
}

class PointPlottedComponent(id: String, position: Vector2D, val label: String) : LabObject(id, position, 40f, 40f) {
    override val name = "Point $label"
    override val type = "PlottedPoint"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}
