package com.arivux.laboratory.interaction

data class FluidContent(
    var name: String,
    var volumeMl: Float,
    var colorHex: String,
    var molarity: Float = 1.0f
)

interface PourableObject {
    var maxCapacityMl: Float
    var currentFluid: FluidContent?
    var tiltAngleDegrees: Float
    
    val tippingAngleDegrees: Float
        get() = 45f // angle at which liquid starts spilling

    fun isSpilling(): Boolean {
        return kotlin.math.abs(tiltAngleDegrees) > tippingAngleDegrees && currentFluid != null && (currentFluid?.volumeMl ?: 0f) > 0f
    }

    fun pour(deltaTime: Float): Float {
        val fluid = currentFluid ?: return 0f
        if (!isSpilling()) return 0f

        // Pour rate increases with tilt angle beyond tipping limit
        val overTilt = kotlin.math.abs(tiltAngleDegrees) - tippingAngleDegrees
        val pourRate = 15f * (overTilt / 10f).coerceIn(0.1f, 3.0f) // mL per second
        val pourAmount = (pourRate * deltaTime).coerceAtMost(fluid.volumeMl)

        fluid.volumeMl -= pourAmount
        if (fluid.volumeMl <= 0f) {
            currentFluid = null
        }
        return pourAmount
    }

    fun receiveFluid(fluid: FluidContent, amountMl: Float) {
        val current = currentFluid
        if (current == null) {
            currentFluid = FluidContent(
                name = fluid.name,
                volumeMl = amountMl.coerceAtMost(maxCapacityMl),
                colorHex = fluid.colorHex,
                molarity = fluid.molarity
            )
        } else {
            // Mix solutions: calculate average molarity and combined volume
            val finalVolume = (current.volumeMl + amountMl).coerceAtMost(maxCapacityMl)
            val totalMoles = (current.volumeMl * current.molarity) + (amountMl * fluid.molarity)
            
            current.molarity = if (finalVolume > 0f) totalMoles / finalVolume else 1.0f
            current.volumeMl = finalVolume
        }
    }
}
