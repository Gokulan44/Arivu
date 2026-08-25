package com.arivux.laboratory.interaction

import com.arivux.laboratory.physics.Vector2D

interface Probe {
    val id: String
    var absolutePosition: Vector2D
}

interface MeasurementTool {
    val toolId: String
    val name: String
    val probes: List<Probe>
    var isCalibrated: Boolean

    fun getMeasurementValue(): Float
    fun getFormattedMeasurement(): String
}
