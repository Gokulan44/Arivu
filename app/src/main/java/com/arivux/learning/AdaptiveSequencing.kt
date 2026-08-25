package com.arivux.learning

import com.arivux.home.LearningNode

class AdaptiveSequencing {
    fun sequenceNextNodes(
        currentCourseNodes: List<LearningNode>,
        lastCompletedNodeId: Int,
        successScore: Float
    ): List<LearningNode> {
        return currentCourseNodes.map { node ->
            if (node.id == lastCompletedNodeId) {
                // Mark current as fully completed
                node.copy(completedCount = node.totalCount)
            } else if (node.id == lastCompletedNodeId + 1 && successScore >= 0.8f) {
                // Unlock next node since user succeeded (>80%)
                node.copy(isLocked = false)
            } else {
                node
            }
        }
    }
}
