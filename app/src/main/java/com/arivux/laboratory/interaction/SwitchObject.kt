package com.arivux.laboratory.interaction

interface SwitchObject {
    var isOn: Boolean

    fun toggle() {
        isOn = !isOn
    }

    fun turnOn() {
        isOn = true
    }

    fun turnOff() {
        isOn = false
    }
}
