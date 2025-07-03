package com.fetch.rewards.exercise.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fetch.rewards.exercise.ListItemViewModel
import com.fetch.rewards.exercise.nav.NavigationItem
import com.fetch.rewards.exercise.views.ShowAllContainer
import com.fetch.rewards.exercise.views.ExerciseAppBar
import com.fetch.rewards.exercise.views.NetworkCallStatus
import com.fetch.rewards.exercise.views.SearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAllScreen(navController: NavController){

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            val vm : ListItemViewModel = hiltViewModel()

            ExerciseAppBar(vm)

            SearchView(vm)
            NetworkCallStatus(vm)
            ShowAllContainer(listItemViewModel = vm, onListByIdCardClicked = { listId ->
                navController.navigate(NavigationItem.ListByListId.route + "?text=${listId}")
            })
        }
    }
}



