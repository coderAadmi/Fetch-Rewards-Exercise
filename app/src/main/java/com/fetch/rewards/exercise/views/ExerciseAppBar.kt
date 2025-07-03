package com.fetch.rewards.exercise.views

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.fetch.rewards.exercise.ListItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseAppBar(vm : ListItemViewModel){
    CenterAlignedTopAppBar(
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp
        ),
        title = { Text("List Items") },
        navigationIcon = {
            ConnectivityStatus(vm.getConnectionStatus(), {
                vm.refresh()
            })
        },
        actions = {
            ListAllIcon(vm)
            GroupIcon(vm)
        }
    )
}