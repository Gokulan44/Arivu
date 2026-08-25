package com.arivux.laboratory.interaction

import com.arivux.laboratory.physics.Vector2D

data class Terminal(
    val id: String,
    val relativeOffset: Vector2D,
    var connectedWireIds: MutableList<String> = mutableListOf(),
    var voltage: Float = 0f,
    var current: Float = 0f
) {
    fun getAbsolutePosition(componentPosition: Vector2D): Vector2D {
        return componentPosition + relativeOffset
    }
}

data class Wire(
    val id: String,
    val fromComponentId: String,
    val fromTerminalId: String,
    val toComponentId: String,
    val toTerminalId: String,
    var colorHex: String = "#3F51B5",
    var currentFlow: Float = 0f
)

interface ConnectableObject {
    val id: String
    val position: Vector2D
    val terminals: List<Terminal>

    fun getTerminal(terminalId: String): Terminal? {
        return terminals.firstOrNull { it.id == terminalId }
    }

    fun getTerminalAbsolutePosition(terminalId: String): Vector2D? {
        return getTerminal(terminalId)?.getAbsolutePosition(position)
    }
}
