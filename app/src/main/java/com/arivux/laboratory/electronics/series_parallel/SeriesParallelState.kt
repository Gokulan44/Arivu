package com.arivux.laboratory.electronics.series_parallel

data class SeriesParallelState(
    var resistanceR1: Float = 100.0f,
    var resistanceR2: Float = 200.0f,
    var measuredEquivalentResistance: Float = 0f,
    
    // "series", "parallel", or "unknown"
    var connectionType: String = "unknown",
    
    var seriesVerified: Boolean = false,
    var parallelVerified: Boolean = false
)
