package com.arivux.project

import java.util.UUID

data class GitHubRepo(
    val name: String,
    val description: String,
    val isPrivate: Boolean
)

class GitHubIntegration {
    var connectedUsername: String? = null
        private set
    
    var isConnected: Boolean = false
        private set

    fun connect(username: String, token: String): Boolean {
        if (username.isBlank() || token.isBlank()) return false
        connectedUsername = username
        isConnected = true
        return true
    }

    fun getRepositories(): List<GitHubRepo> {
        if (!isConnected) return emptyList()
        return listOf(
            GitHubRepo("stem-greenhouse-iot", "Arduino Greenhouse sensors code.", false),
            GitHubRepo("physics-lab-simulations", "Simple pendulum period data logs.", false),
            GitHubRepo("ohms-law-resistors-test", "Circuit simulation sweeps.", true)
        )
    }

    fun pushFiles(repoName: String, files: Map<String, String>): String? {
        if (!isConnected) return null
        // Generate mock commit hash SHA-1
        return UUID.randomUUID().toString().replace("-", "").take(40)
    }

    fun disconnect() {
        connectedUsername = null
        isConnected = false
    }
}
