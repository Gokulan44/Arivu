package com.arivux.laboratory.physics.simple_pendulum

data class SimplePendulumState(
    var lengthL: Float = 1.5f,
    var gravityG: Float = 9.8f,
    var angleRad: Float = 0f,
    var angularVelocity: Float = 0f,
    
    var timePeriodT: Float = 0f,
    var isSwinging: Boolean = false,
    var oscillationsCount: Int = 0,
    
    var lengthConfigured: Boolean = false,
    var swingCompleted: Boolean = false
)
