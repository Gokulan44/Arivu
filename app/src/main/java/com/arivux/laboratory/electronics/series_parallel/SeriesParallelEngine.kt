package com.arivux.laboratory.electronics.series_parallel

import com.arivux.laboratory.engine.DomainSolver
import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.interaction.Wire

class SeriesParallelEngine(
    val state: SeriesParallelState = SeriesParallelState()
) : DomainSolver {

    override fun solve(objects: List<LabObject>, wires: List<Wire>, deltaTime: Float) {
        val r1 = state.resistanceR1
        val r2 = state.resistanceR2

        val connection = detectConnectionType(wires)
        state.connectionType = connection

        when (connection) {
            "series" -> {
                state.measuredEquivalentResistance = r1 + r2
            }
            "parallel" -> {
                state.measuredEquivalentResistance = (r1 * r2) / (r1 + r2)
            }
            else -> {
                state.measuredEquivalentResistance = 0f
            }
        }
    }

    private fun detectConnectionType(wires: List<Wire>): String {
        // Parallel check: both terminals of Resistor 1 connected to terminals of Resistor 2
        val parallelDirect = (isConnected("resistor_1", "a", "resistor_2", "a", wires) &&
                              isConnected("resistor_1", "b", "resistor_2", "b", wires)) ||
                             (isConnected("resistor_1", "a", "resistor_2", "b", wires) &&
                              isConnected("resistor_1", "b", "resistor_2", "a", wires))

        if (parallelDirect) {
            val ohmmeterConnected = wires.any { it.fromComponentId == "ohmmeter_primary" || it.toComponentId == "ohmmeter_primary" }
            if (ohmmeterConnected) return "parallel"
        }

        // Series check: one terminal connected between resistors, others go to ohmmeter
        val seriesMidConnected = isConnected("resistor_1", "b", "resistor_2", "a", wires) ||
                                 isConnected("resistor_1", "a", "resistor_2", "b", wires)
        
        if (seriesMidConnected) {
            val ohmmeterToR1 = wires.any {
                it.fromComponentId == "ohmmeter_primary" && it.toComponentId == "resistor_1" ||
                it.toComponentId == "ohmmeter_primary" && it.fromComponentId == "resistor_1"
            }
            val ohmmeterToR2 = wires.any {
                it.fromComponentId == "ohmmeter_primary" && it.toComponentId == "resistor_2" ||
                it.toComponentId == "ohmmeter_primary" && it.fromComponentId == "resistor_2"
            }
            if (ohmmeterToR1 && ohmmeterToR2) return "series"
        }

        return "unknown"
    }

    private fun isConnected(c1: String, t1: String, c2: String, t2: String, wires: List<Wire>): Boolean {
        return wires.any {
            (it.fromComponentId == c1 && it.fromTerminalId == t1 && it.toComponentId == c2 && it.toTerminalId == t2) ||
            (it.fromComponentId == c2 && it.fromTerminalId == t2 && it.toComponentId == c1 && it.toTerminalId == t1)
        }
    }
}
