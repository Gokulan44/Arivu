package com.arivux.laboratory.biology.microscope

data class MicroscopeState(
    var coarseFocusValue: Float = 0f,
    var fineFocusValue: Float = 0f,
    var currentLensIndex: Int = 0,
    var activeSpecimenId: String = "",
    var isSlidePlaced: Boolean = false,
    
    var stainApplied: String = "none",
    var contrastFactor: Float = 0.15f, // faint by default
    var blurRadius: Float = 20.0f,
    var imageClarityPercent: Float = 0f,
    
    var slidePlacedCorrectly: Boolean = false,
    var focusCompleted: Boolean = false
)
