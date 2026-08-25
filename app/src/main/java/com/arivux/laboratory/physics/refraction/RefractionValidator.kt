package com.arivux.laboratory.physics.refraction

class RefractionValidator(
    private val state: RefractionState
) {
    fun verifySnellsLaw(): Boolean {
        return state.isSnellsLawVerified
    }
}
