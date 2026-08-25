package com.arivux.laboratory.mathematics.coordinate_geometry

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.mathematics.CoordinateGrid
import kotlin.math.sqrt

class CoordinateGeometryEngine(
    val state: CoordinateGeometryState = CoordinateGeometryState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val grid = objects.firstOrNull { it is CoordinateGrid } as? CoordinateGrid ?: return
        
        // Find Point A and Point B (represented as Draggable points)
        val pointA = objects.firstOrNull { it.id == "point_a" }
        val pointB = objects.firstOrNull { it.id == "point_b" }

        if (pointA != null && pointB != null) {
            // Translate pixel positions relative to grid centers to standard cartesian
            state.ax = translatePixelToCartesianX(pointA.position.x, grid)
            state.ay = translatePixelToCartesianY(pointA.position.y, grid)
            
            state.bx = translatePixelToCartesianX(pointB.position.x, grid)
            state.by = translatePixelToCartesianY(pointB.position.y, grid)

            val dx = state.bx - state.ax
            val dy = state.by - state.ay

            // Calculate Distance: sqrt(dx^2 + dy^2)
            state.calculatedDistance = sqrt(dx * dx + dy * dy)

            // Calculate Slope (m = dy/dx) and Intercept (c = y - mx)
            if (kotlin.math.abs(dx) > 0.001f) {
                val m = dy / dx
                state.calculatedSlope = m
                state.calculatedIntercept = state.ay - m * state.ax
                state.lineEquation = String.format("y = %.2fx + %.2f", m, state.calculatedIntercept)
            } else {
                state.calculatedSlope = Float.POSITIVE_INFINITY
                state.lineEquation = String.format("x = %.2f", state.ax)
            }

            // Verify objectives
            state.pointsPlacedCorrectly = kotlin.math.abs(state.ax - 2f) < 0.2f && kotlin.math.abs(state.ay - 3f) < 0.2f &&
                                           kotlin.math.abs(state.bx - 6f) < 0.2f && kotlin.math.abs(state.by - 6f) < 0.2f
            state.slopeVerified = kotlin.math.abs(state.calculatedSlope - 0.75f) < 0.05f
        }
    }

    private fun translatePixelToCartesianX(px: Float, grid: CoordinateGrid): Float {
        val centerX = grid.position.x + grid.width / 2f
        val scaleX = grid.width / (grid.xMax - grid.xMin)
        return (px - centerX) / scaleX
    }

    private fun translatePixelToCartesianY(py: Float, grid: CoordinateGrid): Float {
        val centerY = grid.position.y + grid.height / 2f
        val scaleY = grid.height / (grid.yMax - grid.yMin)
        return -(py - centerY) / scaleY // invert Y axis
    }
}
