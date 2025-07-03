package com.fetch.rewards.exercise.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.fetch.rewards.exercise.ListItemViewModel

@Composable
fun ShowByIdContianer(listItemViewModel: ListItemViewModel,
                      listId : Int) {

        var items = listItemViewModel.listState.collectAsState().value
        if(listItemViewModel.isFilterActive.collectAsState().value){
                 items = listItemViewModel.getFilteredList().collectAsState().value
        }

        ListByIdListView(items.filter { it ->
            it.listId == listId
        })

}