package com.fetch.rewards.exercise.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fetch.rewards.exercise.ListItemViewModel
import com.fetch.rewards.exercise.views.ShowByIdContianer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowByIdScreen(navController: NavController, listId : Int){

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            val vm : ListItemViewModel = hiltViewModel()

            CenterAlignedTopAppBar(
                windowInsets = WindowInsets(
                    top = 0.dp,
                    bottom = 0.dp
                ),
                title = { Text("ListId = $listId") },
                navigationIcon = { IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "action item for selecting list presentation"
                    )
                }}
            );

            ShowByIdContianer(listItemViewModel = vm, listId)
        }
    }
}