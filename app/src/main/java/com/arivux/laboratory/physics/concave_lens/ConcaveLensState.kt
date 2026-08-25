package com.arivux.laboratory.physics.concave_lens

data class ConcaveLensState(
    var focalLengthF: Float = -15.0f, // Negative focal length for concave lens
    var convexFocalLength: Float = 20.0f, // Auxiliary lens focal length
    
    var objectPositionCm: Float = 10f,
    var convexLensPositionCm: Float = 40f,
    var concaveLensPositionCm: Float = 55f,
    var screenPositionCm: Float = 85f,

    var objectDistanceU: Float = 0f,
    var imageDistanceV: Float = 0f,
    
    var imageClarityPercent: Float = 0f,
    var isFocused: Boolean = false
)
