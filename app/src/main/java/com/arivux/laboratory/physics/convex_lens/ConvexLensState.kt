package com.arivux.laboratory.physics.convex_lens

data class ConvexLensState(
    var focalLengthF: Float = 20.0f, // Focal length in cm
    var objectPositionCm: Float = 10f, // 0 - 100 cm on bench
    var lensPositionCm: Float = 40f,
    var screenPositionCm: Float = 80f,

    var objectDistanceU: Float = 0f,
    var imageDistanceV: Float = 0f,
    var imageClarityPercent: Float = 0f,
    var isFocused: Boolean = false,

    // Ray tracing coordinates (drawn on canvas)
    var ray1StartX: Float = 0f,
    var ray1StartY: Float = 0f,
    var ray1LensX: Float = 0f,
    var ray1LensY: Float = 0f,
    var ray1EndX: Float = 0f,
    var ray1EndY: Float = 0f,

    var ray2StartX: Float = 0f,
    var ray2StartY: Float = 0f,
    var ray2LensX: Float = 0f,
    var ray2LensY: Float = 0f,
    var ray2EndX: Float = 0f,
    var ray2EndY: Float = 0f
)
