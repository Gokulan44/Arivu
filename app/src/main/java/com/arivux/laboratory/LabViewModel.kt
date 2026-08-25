package com.arivux.laboratory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arivux.laboratory.engine.*
import com.arivux.laboratory.physics.Vector2D
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LabViewModel : ViewModel() {
    val labEngine = LabEngine()
    
    val workspaceState: StateFlow<LabWorkspaceState> = labEngine.workspaceStateFlow

    init {
        // Start simulation loop on initialization
        labEngine.startSimulation()
    }

    fun handleTouchDown(x: Float, y: Float) {
        labEngine.touchController.handleTouchDown(Vector2D(x, y))
    }

    fun handleTouchMove(x: Float, y: Float) {
        labEngine.touchController.handleTouchMove(Vector2D(x, y))
    }

    fun handleTouchUp(x: Float, y: Float) {
        labEngine.touchController.handleTouchUp(Vector2D(x, y))
    }

    fun undo() {
        labEngine.stateManager.undo()
        labEngine.notifyStateChanged()
    }

    fun redo() {
        labEngine.stateManager.redo()
        labEngine.notifyStateChanged()
    }

    fun clearWorkspace() {
        labEngine.stateManager.clear()
        labEngine.physicsWorld.clear()
        labEngine.notifyStateChanged()
    }

    fun addComponent(type: String, position: Vector2D) {
        val newObj = when (type) {
            "Battery" -> BatteryComponent("battery_${System.currentTimeMillis()}", position)
            "Resistor" -> ResistorComponent("resistor_${System.currentTimeMillis()}", position)
            "Bulb" -> BulbComponent("bulb_${System.currentTimeMillis()}", position)
            "Switch" -> SwitchComponent("switch_${System.currentTimeMillis()}", position)
            else -> null
        }
        if (newObj != null) {
            labEngine.stateManager.addObject(newObj)
            labEngine.notifyStateChanged()
        }
    }

    override fun onCleared() {
        super.onCleared()
        labEngine.destroy()
    }
}

// Simple placeholders to allow compiling before Ohm's Law Objects are fully defined
class BatteryComponent(id: String, position: Vector2D) : LabObject(id, position, 140f, 80f) {
    override val name = "Battery (9V)"
    override val type = "Battery"
    override val terminals = listOf(
        com.arivux.laboratory.interaction.Terminal("pos", Vector2D(0f, 40f)),
        com.arivux.laboratory.interaction.Terminal("neg", Vector2D(140f, 40f))
    )
}

class ResistorComponent(id: String, position: Vector2D) : LabObject(id, position, 120f, 60f) {
    override val name = "Resistor (100Ω)"
    override val type = "Resistor"
    override val terminals = listOf(
        com.arivux.laboratory.interaction.Terminal("a", Vector2D(0f, 30f)),
        com.arivux.laboratory.interaction.Terminal("b", Vector2D(120f, 30f))
    )
}

class BulbComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 100f) {
    override val name = "Light Bulb"
    override val type = "Bulb"
    override val terminals = listOf(
        com.arivux.laboratory.interaction.Terminal("a", Vector2D(0f, 50f)),
        com.arivux.laboratory.interaction.Terminal("b", Vector2D(100f, 50f))
    )
}

class SwitchComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 60f), InteractiveComponent {
    override val name = "Switch (Open)"
    override val type = "Switch"
    var isOpen = true
    override val terminals = listOf(
        com.arivux.laboratory.interaction.Terminal("a", Vector2D(0f, 30f)),
        com.arivux.laboratory.interaction.Terminal("b", Vector2D(100f, 30f))
    )

    override fun handleTap(relativePoint: Vector2D): Boolean {
        isOpen = !isOpen
        return true
    }
}
