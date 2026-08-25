package com.arivux.laboratory.electronics.kirchhoff_laws

class KirchhoffMeasurements(
    private val state: KirchhoffState
) {
    fun getResistor1VoltageReading(): String {
        return String.format("%.2f V", state.voltageR1)
    }

    fun getResistor2VoltageReading(): String {
        return String.format("%.2f V", state.voltageR2)
    }

    fun getResistor3VoltageReading(): String {
        return String.format("%.2f V", state.voltageR3)
    }

    fun getBranch1CurrentReading(): String {
        return String.format("%.3f A", state.currentI1)
    }

    fun getBranch2CurrentReading(): String {
        return String.format("%.3f A", state.currentI2)
    }

    fun getBranch3CurrentReading(): String {
        return String.format("%.3f A", state.currentI3)
    }
}
