package com.fetch.rewards.exercise.views

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.fetch.rewards.exercise.ListItemViewModel
import com.fetch.rewards.exercise.R
import com.fetch.rewards.exercise.network.ConnectivityStatus

@Composable
fun ListAllIcon(vm: ListItemViewModel) {
    IconButton(onClick = {
        vm.editDefaultTypeSelected(true)
        Log.d("RES_TOG", "Type = " + vm.isDefaultTypeSelected.value)
    }) {
        if (vm.isDefaultTypeSelected.collectAsState().value) {
            Icon(
                painter = painterResource(R.drawable.checklist_green),
                contentDescription = "action item for selecting list presentation",
                tint = MaterialTheme.colorScheme.secondary
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.checklist_gray),
                contentDescription = "action item for selecting list presentation",
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun GroupIcon(vm: ListItemViewModel) {
    IconButton(onClick = {
        vm.editDefaultTypeSelected(false)
        Log.d("RES_TOG", "Type = " + vm.isDefaultTypeSelected.value)
    }) {
        if (vm.isDefaultTypeSelected.collectAsState().value) {
            Icon(
                painter = painterResource(R.drawable.group_gray),
                contentDescription = "action item for selecting list presentation",
                tint = MaterialTheme.colorScheme.tertiary
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.group_green),
                contentDescription = "action item for selecting list presentation",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
