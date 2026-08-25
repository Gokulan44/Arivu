package com.arivux.laboratory.engine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class LabSimulationEngine(
    private val onTick: (Float) -> Unit
) {
    private var simulationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var simulationJob: Job? = null
    
    private val _tickFlow = MutableSharedFlow<Float>()
    val tickFlow: SharedFlow<Float> = _tickFlow

    var isRunning = false
        private set

    fun start() {
        if (isRunning) return
        isRunning = true
        simulationJob = simulationScope.launch {
            var lastTime = System.currentTimeMillis()
            while (isActive && isRunning) {
                val currentTime = System.currentTimeMillis()
                val deltaTime = (currentTime - lastTime) / 1000f // in seconds
                lastTime = currentTime

                // Ensure non-zero and reasonable delta to prevent physics jumps
                val clampedDelta = deltaTime.coerceIn(0.001f, 0.05f)
                
                withContext(Dispatchers.Main) {
                    onTick(clampedDelta)
                    _tickFlow.emit(clampedDelta)
                }

                delay(16) // Target ~60 FPS
            }
        }
    }

    fun stop() {
        isRunning = false
        simulationJob?.cancel()
        simulationJob = null
    }

    fun pause() {
        isRunning = false
    }

    fun resume() {
        start()
    }

    fun destroy() {
        stop()
        simulationScope.cancel()
    }
}
