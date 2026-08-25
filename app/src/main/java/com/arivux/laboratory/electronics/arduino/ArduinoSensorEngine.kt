package com.arivux.laboratory.electronics.arduino

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D
import com.arivux.laboratory.interaction.Terminal
import com.arivux.laboratory.interaction.SliderObject
import com.arivux.laboratory.interaction.SwitchObject

class ArduinoTemperatureSensorComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 80f), SliderObject {
    override val name = "LM35 Temp Sensor"
    override val type = "ArduinoTempSensor"
    override val terminals = listOf(
        Terminal("vcc", Vector2D(10f, 60f)),
        Terminal("out", Vector2D(50f, 60f)),
        Terminal("gnd", Vector2D(90f, 60f))
    )

    // Slider simulates room temperature from -10°C to 110°C
    override val minValue = -10f
    override val maxValue = 110f
    override var currentValue = 25f // default room temp
}

class ArduinoMotionSensorComponent(id: String, position: Vector2D) : LabObject(id, position, 100f, 80f), SwitchObject {
    override val name = "PIR Motion Sensor"
    override val type = "ArduinoMotionSensor"
    override val terminals = listOf(
        Terminal("vcc", Vector2D(10f, 60f)),
        Terminal("out", Vector2D(50f, 60f)),
        Terminal("gnd", Vector2D(90f, 60f))
    )

    // Switch toggles motion detection state
    override var isOn = false
}

class ArduinoSensorEngine {
    fun updateSensors(objects: List<LabObject>, board: ArduinoBoard, wires: List<com.arivux.laboratory.interaction.Wire>) {
        // 1. Update Temperature Sensor -> Pin A0
        val tempSensor = objects.firstOrNull { it is ArduinoTemperatureSensorComponent } as? ArduinoTemperatureSensorComponent
        if (tempSensor != null) {
            val isA0Connected = isPinConnectedToComponent(board.id, "A0", tempSensor.id, "out", wires)
            if (isA0Connected) {
                // LM35 outputs 10mV/°C. Output = TempC * 0.01V
                // Arduino ADC reads 0 to 1023 for 0V to 5V.
                // ADC = (Output / 5.0) * 1023
                val tempC = tempSensor.currentValue
                val voltage = tempC * 0.01f
                val adc = ((voltage / 5.0f) * 1023f).coerceIn(0f, 1023f).toInt()
                board.pins["A0"]?.analogValue = adc
            }
        }

        // 2. Update Motion Sensor -> Pin D2
        val motionSensor = objects.firstOrNull { it is ArduinoMotionSensorComponent } as? ArduinoMotionSensorComponent
        if (motionSensor != null) {
            val isD2Connected = isPinConnectedToComponent(board.id, "D2", motionSensor.id, "out", wires)
            if (isD2Connected) {
                board.pins["D2"]?.digitalState = if (motionSensor.isOn) PinState.HIGH else PinState.LOW
            }
        }
    }

    private fun isPinConnectedToComponent(boardId: String, pinId: String, compId: String, termId: String, wires: List<com.arivux.laboratory.interaction.Wire>): Boolean {
        return wires.any {
            (it.fromComponentId == boardId && it.fromTerminalId == pinId && it.toComponentId == compId && it.toTerminalId == termId) ||
            (it.fromComponentId == compId && it.fromTerminalId == termId && it.toComponentId == boardId && it.toTerminalId == pinId)
        }
    }
}
