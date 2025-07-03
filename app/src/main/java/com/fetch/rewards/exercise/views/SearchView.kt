package com.fetch.rewards.exercise.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fetch.rewards.exercise.ListItemViewModel

@Composable
fun SearchView( vm : ListItemViewModel){
    OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        value = vm.searchState.collectAsState().value,
        onValueChange = { changedValue ->
            vm.setSearchValue(changedValue)
        },
        leadingIcon = {Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "search list item by name"
        )},
        label = {Text("Search list item by name")},
        placeholder = {Text("Enter name of item")},
        trailingIcon = {
            if(vm.searchState.collectAsState().value.isNotEmpty()){
                IconButton(onClick = {
                    vm.setSearchValue("")
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "clear search result"
                    )
                }
            }
        }
    )
}