package com.arivux.laboratory.physics.concave_lens

import com.arivux.laboratory.engine.LabObject
import com.arivux.laboratory.physics.Vector2D

class ConcaveLensBenchComponent(id: String, position: Vector2D) : LabObject(id, position, 800f, 60f) {
    override val name = "Optical Bench Scale (0-100cm)"
    override val type = "ConcaveLensBench"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class ConcaveLightSourceComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 100f) {
    override val name = "Light Source"
    override val type = "ConcaveLightSource"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class ConcaveConvexLensComponent(id: String, position: Vector2D, val f: Float) : LabObject(id, position, 60f, 120f) {
    override val name = "Aux Convex Lens (f=$f cm)"
    override val type = "ConcaveConvexLens"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class ConcaveLensComponent(id: String, position: Vector2D, val f: Float) : LabObject(id, position, 60f, 120f) {
    override val name = "Concave Lens (f=$f cm)"
    override val type = "ConcaveLens"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}

class ConcaveScreenComponent(id: String, position: Vector2D) : LabObject(id, position, 80f, 120f) {
    override val name = "Image Screen"
    override val type = "ConcaveScreen"
    override val terminals = emptyList<com.arivux.laboratory.interaction.Terminal>()
}
