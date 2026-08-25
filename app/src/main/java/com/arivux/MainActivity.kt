package com.arivux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arivux.home.HomeScreen
import com.arivux.laboratory.LabScreen
import com.arivux.laboratory.LabViewModel
import com.arivux.leaderboard.LeaderboardScreen
import com.arivux.portfolio.PortfolioScreen
import com.arivux.project.ProjectScreen
import com.arivux.settings.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArivuMainShell()
        }
    }
}

enum class ScreenTab(val title: String, val icon: String) {
    Home("Home", "🏠"),
    Lab("Lab", "🧪"),
    Projects("Projects", "🤖"),
    Leaderboard("Leaderboard", "🏆"),
    Portfolio("Portfolio", "👦"),
    Settings("Settings", "⚙️")
}

@Composable
fun ArivuMainShell() {
    var currentTab by remember { mutableStateOf(ScreenTab.Home) }
    val labViewModel: LabViewModel = viewModel()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = androidx.compose.ui.graphics.Color.White,
                contentColor = androidx.compose.ui.graphics.Color(0xFF4F46E5)
            ) {
                ScreenTab.values().forEach { tab ->
                    BottomNavigationItem(
                        icon = { Text(tab.icon, fontSize = 20.sp) },
                        label = { Text(tab.title, fontSize = 10.sp) },
                        selected = currentTab == tab,
                        onClick = { currentTab = tab }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (currentTab) {
                ScreenTab.Home -> HomeScreen(
                    onNodeSelect = { node ->
                        currentTab = ScreenTab.Lab
                    }
                )
                ScreenTab.Lab -> LabScreen(viewModel = labViewModel)
                ScreenTab.Projects -> ProjectScreen()
                ScreenTab.Leaderboard -> LeaderboardScreen()
                ScreenTab.Portfolio -> PortfolioScreen()
                ScreenTab.Settings -> SettingsScreen()
            }
        }
    }
}
