package com.arivux.laboratory.engine

import com.arivux.laboratory.interaction.ConnectableObject
import com.arivux.laboratory.interaction.Terminal
import com.arivux.laboratory.interaction.Wire
import com.arivux.laboratory.physics.Vector2D
import java.util.UUID

sealed class TouchAction {
    object Idle : TouchAction()
    data class DraggingObject(val obj: LabObject) : TouchAction()
    data class ConnectingWire(val fromComponentId: String, val fromTerminalId: String, var currentTouchPoint: Vector2D) : TouchAction()
}

class LabTouchController(
    private val stateManager: LabStateManager,
    private val dragController: LabDragController,
    private val onStateChanged: () -> Unit
) {
    var currentAction: TouchAction = TouchAction.Idle
        private set

    fun handleTouchDown(touchPoint: Vector2D) {
        val objects = stateManager.getObjects()

        // 1. Check if user tapped a terminal socket first (to connect wire)
        for (obj in objects) {
            for (terminal in obj.terminals) {
                val absoluteTerminalPos = terminal.getAbsolutePosition(obj.position)
                if (touchPoint.distance(absoluteTerminalPos) < 30f) {
                    currentAction = TouchAction.ConnectingWire(
                        fromComponentId = obj.id,
                        fromTerminalId = terminal.id,
                        currentTouchPoint = touchPoint
                    )
                    onStateChanged()
                    return
                }
            }
        }

        // 2. Check if user tapped an interactive object
        val tappedObj = objects.lastOrNull { it.containsPoint(touchPoint) }
        if (tappedObj != null) {
            // Check for specific interactive traits (e.g. toggles, buttons)
            if (tappedObj is InteractiveComponent && tappedObj.handleTap(touchPoint - tappedObj.position)) {
                onStateChanged()
                return
            }
            
            // Otherwise, initiate dragging
            dragController.startDragging(tappedObj, touchPoint)
            currentAction = TouchAction.DraggingObject(tappedObj)
            onStateChanged()
            return
        }

        currentAction = TouchAction.Idle
    }

    fun handleTouchMove(touchPoint: Vector2D) {
        when (val action = currentAction) {
            is TouchAction.DraggingObject -> {
                // Gather snap points from other terminals (for alignment)
                val snapPoints = stateManager.getObjects()
                    .filter { it.id != action.obj.id }
                    .flatMap { obj -> obj.terminals.map { it.getAbsolutePosition(obj.position) } }
                
                dragController.drag(touchPoint, snapPoints)
                onStateChanged()
            }
            is TouchAction.ConnectingWire -> {
                action.currentTouchPoint = touchPoint
                onStateChanged()
            }
            else -> {}
        }
    }

    fun handleTouchUp(touchPoint: Vector2D) {
        when (val action = currentAction) {
            is TouchAction.DraggingObject -> {
                dragController.stopDragging()
                currentAction = TouchAction.Idle
                onStateChanged()
            }
            is TouchAction.ConnectingWire -> {
                // Check if dropped near another terminal
                var wireCreated = false
                val objects = stateManager.getObjects()
                
                for (obj in objects) {
                    if (obj.id == action.fromComponentId) continue
                    for (terminal in obj.terminals) {
                        val absoluteTerminalPos = terminal.getAbsolutePosition(obj.position)
                        if (touchPoint.distance(absoluteTerminalPos) < 40f) {
                            // Create the connection wire!
                            val newWire = Wire(
                                id = UUID.randomUUID().toString(),
                                fromComponentId = action.fromComponentId,
                                fromTerminalId = action.fromTerminalId,
                                toComponentId = obj.id,
                                toTerminalId = terminal.id
                            )
                            stateManager.addWire(newWire)
                            wireCreated = true
                            break
                        }
                    }
                    if (wireCreated) break
                }
                
                currentAction = TouchAction.Idle
                onStateChanged()
            }
            else -> {}
        }
    }
}

interface InteractiveComponent {
    fun handleTap(relativePoint: Vector2D): Boolean
}
