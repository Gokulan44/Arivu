package com.arivux.laboratory.engine

import com.arivux.laboratory.interaction.DraggableObject
import com.arivux.laboratory.interaction.ConnectableObject
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.physics.Vector2D

abstract class LabObject(
    override val id: String,
    override var position: Vector2D,
    override val width: Float,
    override val height: Float,
    override var isDragging: Boolean = false
) : DraggableObject, ConnectableObject {
    abstract val name: String
    abstract val type: String
}

data class LabWorkspaceState(
    val objects: List<LabObjectState>,
    val wires: List<Wire>
)

data class LabObjectState(
    val id: String,
    val type: String,
    val x: Float,
    val y: Float,
    val properties: Map<String, String>
)

class LabStateManager {
    private val activeObjects = mutableListOf<LabObject>()
    private val activeWires = mutableListOf<Wire>()
    
    private val undoStack = mutableListOf<LabWorkspaceState>()
    private val redoStack = mutableListOf<LabWorkspaceState>()

    fun getObjects(): List<LabObject> = activeObjects
    fun getWires(): List<Wire> = activeWires

    fun addObject(obj: LabObject) {
        saveStateToUndo()
        activeObjects.add(obj)
    }

    fun removeObject(id: String) {
        saveStateToUndo()
        activeObjects.removeAll { it.id == id }
        // Clean up connected wires
        activeWires.removeAll { it.fromComponentId == id || it.toComponentId == id }
    }

    fun addWire(wire: Wire) {
        saveStateToUndo()
        activeWires.add(wire)
        // Update terminals
        activeObjects.firstOrNull { it.id == wire.fromComponentId }
            ?.getTerminal(wire.fromTerminalId)?.connectedWireIds?.add(wire.id)
        activeObjects.firstOrNull { it.id == wire.toComponentId }
            ?.getTerminal(wire.toTerminalId)?.connectedWireIds?.add(wire.id)
    }

    fun removeWire(wireId: String) {
        saveStateToUndo()
        val wire = activeWires.firstOrNull { it.id == wireId }
        if (wire != null) {
            activeWires.remove(wire)
            activeObjects.firstOrNull { it.id == wire.fromComponentId }
                ?.getTerminal(wire.fromTerminalId)?.connectedWireIds?.remove(wireId)
            activeObjects.firstOrNull { it.id == wire.toComponentId }
                ?.getTerminal(wire.toTerminalId)?.connectedWireIds?.remove(wireId)
        }
    }

    fun clear() {
        saveStateToUndo()
        activeObjects.clear()
        activeWires.clear()
    }

    fun captureSnapshot(): LabWorkspaceState {
        return LabWorkspaceState(
            objects = activeObjects.map { obj ->
                LabObjectState(
                    id = obj.id,
                    type = obj.type,
                    x = obj.position.x,
                    y = obj.position.y,
                    properties = emptyMap() // Can be extended by subclasses
                )
            },
            wires = activeWires.toList()
        )
    }

    fun restoreSnapshot(state: LabWorkspaceState) {
        // Concrete restore logic will be implemented by the bootstrapping LabEngine
    }

    private fun saveStateToUndo() {
        if (undoStack.size > 20) {
            undoStack.removeAt(0)
        }
        undoStack.add(captureSnapshot())
        redoStack.clear()
    }

    fun undo() {
        if (undoStack.isNotEmpty()) {
            val currentState = captureSnapshot()
            redoStack.add(currentState)
            val previousState = undoStack.removeAt(undoStack.size - 1)
            restoreSnapshot(previousState)
        }
    }

    fun redo() {
        if (redoStack.isNotEmpty()) {
            val currentState = captureSnapshot()
            undoStack.add(currentState)
            val nextState = redoStack.removeAt(redoStack.size - 1)
            restoreSnapshot(nextState)
        }
    }
}
