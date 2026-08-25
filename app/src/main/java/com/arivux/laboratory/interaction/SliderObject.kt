package com.arivux.laboratory.interaction

interface SliderObject {
    val minValue: Float
    val maxValue: Float
    var currentValue: Float

    fun updateValue(fraction: Float) {
        val nextValue = minValue + fraction.coerceIn(0f, 1f) * (maxValue - minValue)
        currentValue = nextValue
    }

    fun getValueFraction(): Float {
        val range = maxValue - minValue
        return if (range > 0f) (currentValue - minValue) / range else 0f
    }
}
