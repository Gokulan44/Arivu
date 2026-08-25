package com.arivux.laboratory.electronics.arduino

class ArduinoCodeEngine {
    var activeSketchName = "Blink" // "Blink", "Temperature", "Motion"
    val serialMonitorOutput = mutableListOf<String>()
    
    private var executionTimer = 0f
    
    fun tick(board: ArduinoBoard, deltaTime: Float) {
        executionTimer += deltaTime
        
        when (activeSketchName) {
            "Blink" -> {
                // Pin 13 set to OUTPUT, oscillates state every 1.0 seconds
                val pin13 = board.pins["D13"]
                if (pin13 != null) {
                    pin13.mode = PinMode.OUTPUT
                    if (executionTimer >= 1.0f) {
                        executionTimer = 0f
                        pin13.digitalState = if (pin13.digitalState == PinState.LOW) PinState.HIGH else PinState.LOW
                    }
                }
            }
            "Temperature" -> {
                // Pin A0 reads analog temperature, logs to serial buffer
                val pinA0 = board.pins["A0"]
                if (pinA0 != null) {
                    pinA0.mode = PinMode.INPUT
                    val voltage = pinA0.analogValue * (5.0f / 1023f)
                    val tempC = voltage * 100f

                    if (executionTimer >= 1.5f) {
                        executionTimer = 0f
                        if (serialMonitorOutput.size > 20) {
                            serialMonitorOutput.removeAt(0)
                        }
                        serialMonitorOutput.add(String.format("Analog Read: %d | Temp: %.1f °C", pinA0.analogValue, tempC))
                    }
                }
            }
            "Motion" -> {
                // Pin D2 reads digital PIR motion sensor, triggers Pin D8 Buzzer output
                val pin2 = board.pins["D2"]
                val pin8 = board.pins["D8"]
                if (pin2 != null && pin8 != null) {
                    pin2.mode = PinMode.INPUT
                    pin8.mode = PinMode.OUTPUT
                    
                    if (pin2.digitalState == PinState.HIGH) {
                        pin8.digitalState = PinState.HIGH
                    } else {
                        pin8.digitalState = PinState.LOW
                    }
                }
            }
        }
    }

    fun clearSerial() {
        serialMonitorOutput.clear()
    }
}
