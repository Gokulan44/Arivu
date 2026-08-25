package com.arivux.gamification

import com.arivux.leaderboard.LeaderboardUser

class LeaderboardRepository {
    private val topUsers = mutableListOf(
        LeaderboardUser(1, "Riya Sharma", "Lab Master", 8, 2450, 15, 32, "👧"),
        LeaderboardUser(2, "Arjun Patel", "Circuit Expert", 7, 2100, 11, 28, "👦"),
        LeaderboardUser(3, "Meera Nair", "Code Creator", 7, 1980, 10, 24, "👧")
    )

    private val rankingList = mutableListOf(
        LeaderboardUser(4, "Arun Kumar (You)", "STEM Explorer", 7, 1746, 12, 18, "👦"),
        LeaderboardUser(5, "Kabir Singh", "Robot Builder", 6, 1520, 10, 15, "👦"),
        LeaderboardUser(6, "Ananya Iyer", "Tech Innovator", 6, 1380, 9, 14, "👧"),
        LeaderboardUser(7, "Vivaan Gupta", "Circuit Expert", 5, 1240, 8, 12, "👦"),
        LeaderboardUser(8, "Sanavi Reddy", "Code Creator", 5, 1100, 7, 10, "👧")
    )

    fun getTopUsers(): List<LeaderboardUser> {
        return topUsers.sortedBy { it.rank }
    }

    fun getRankingList(): List<LeaderboardUser> {
        return rankingList.sortedBy { it.rank }
    }

    fun updateMyXP(newXP: Int, newLevel: Int) {
        // Find "Arun Kumar (You)" and update value
        val index = rankingList.indexOfFirst { it.name.startsWith("Arun Kumar") }
        if (index != -1) {
            val oldUser = rankingList[index]
            rankingList[index] = oldUser.copy(xp = newXP, level = newLevel)
        }

        // Re-sort combined list to compute new ranks
        val allUsers = (topUsers + rankingList).sortedByDescending { it.xp }
        
        val updatedTop = mutableListOf<LeaderboardUser>()
        val updatedRanking = mutableListOf<LeaderboardUser>()

        allUsers.forEachIndexed { i, user ->
            val rank = i + 1
            val updatedUser = user.copy(rank = rank)
            if (rank <= 3) {
                updatedTop.add(updatedUser)
            } else {
                updatedRanking.add(updatedUser)
            }
        }

        topUsers.clear()
        topUsers.addAll(updatedTop)
        
        rankingList.clear()
        rankingList.addAll(updatedRanking)
    }
}
