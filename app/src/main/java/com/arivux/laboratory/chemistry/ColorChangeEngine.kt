package com.arivux.laboratory.chemistry

class ColorChangeEngine {
    
    // Phenolphthalein: colorless below 8.2, pink above 10.0
    // Bromothymol Blue: yellow below 6.0, green 6.0-7.6, blue above 7.6
    
    fun getSolutionColor(indicator: String, ph: Float): String {
        return when (indicator.lowercase()) {
            "phenolphthalein" -> {
                when {
                    ph < 8.2f -> "#D0E0FF" // Pale transparent bluish-water color
                    ph > 10.0f -> "#FF007F" // Bright pink/magenta
                    else -> {
                        // Interpolate alpha/color value between colorless and pink
                        val factor = (ph - 8.2f) / (10.0f - 8.2f)
                        interpolateColor("#D0E0FF", "#FF007F", factor)
                    }
                }
            }
            "bromothymol_blue" -> {
                when {
                    ph < 6.0f -> "#FACC15" // Yellow
                    ph > 7.6f -> "#1D4ED8" // Blue
                    else -> {
                        val factor = (ph - 6.0f) / (7.6f - 6.0f)
                        interpolateColor("#FACC15", "#1D4ED8", factor)
                    }
                }
            }
            else -> "#D0E0FF" // Standard water
        }
    }

    private fun interpolateColor(startHex: String, endHex: String, fraction: Float): String {
        val sCol = android.graphics.Color.parseColor(startHex)
        val eCol = android.graphics.Color.parseColor(endHex)
        
        val r = (android.graphics.Color.red(sCol) + fraction * (android.graphics.Color.red(eCol) - android.graphics.Color.red(sCol))).toInt()
        val g = (android.graphics.Color.green(sCol) + fraction * (android.graphics.Color.green(eCol) - android.graphics.Color.green(sCol))).toInt()
        val b = (android.graphics.Color.blue(sCol) + fraction * (android.graphics.Color.blue(eCol) - android.graphics.Color.blue(sCol))).toInt()
        
        return String.format("#%02X%02X%02X", r, g, b)
    }
}
