package com.arivux.laboratory.physics.convex_lens

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D

class OpticalBenchComponent(id: String, position: Vector2D) : LabObject(id, position, 800f, 60f) {
    override val name = "Optical Bench Scale (0-100cm)"
    override val type = "OpticalBench"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class LightSourceComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 100f) {
    override val name = "Light Object"
    override val type = "LightSource"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class ConvexLensComponent(id: String, position: Vector2D, val f: Float) : LabObject(id, position, 60f, 120f) {
    override val name = "Convex Lens (f=$f cm)"
    override val type = "ConvexLens"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class ScreenComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 120f) {
    override val name = "Image Screen"
    override val type = "ImageScreen"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}
