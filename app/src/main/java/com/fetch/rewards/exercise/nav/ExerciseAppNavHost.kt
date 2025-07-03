package com.fetch.rewards.exercise.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fetch.rewards.exercise.screens.ShowAllScreen
import com.fetch.rewards.exercise.screens.ShowByIdScreen

@Composable
fun ExerciseAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.ListAll.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.ListAll.route) {
            ShowAllScreen(navController)
        }
        composable(
            NavigationItem.ListByListId.route+ "?text={list_id}",
            arguments = listOf(
                navArgument("list_id") {
                    type = NavType.IntType
                    nullable = false
                }
            )) {
            ShowByIdScreen(navController, it.arguments!!.getInt("list_id"))
        }
    }
}