package com.fetch.rewards.exercise.nav

enum class Screen {
    ShowAllScreen,
    ShowByIdScreen
}

sealed class NavigationItem(val route: String) {
    object ListAll : NavigationItem(Screen.ShowAllScreen.name)
    object ListByListId : NavigationItem(Screen.ShowByIdScreen.name)
}