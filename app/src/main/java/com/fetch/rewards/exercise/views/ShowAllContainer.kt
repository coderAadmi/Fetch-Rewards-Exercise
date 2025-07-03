package com.fetch.rewards.exercise.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.fetch.rewards.exercise.ListItemViewModel


@Composable
fun ShowAllContainer(listItemViewModel: ListItemViewModel, onListByIdCardClicked : (listId : Int) ->Unit) {

    var items = listItemViewModel.listState.collectAsState().value

    if(listItemViewModel.isFilterActive.collectAsState().value){
        items = listItemViewModel.getFilteredList().collectAsState().value
    }

    if (listItemViewModel.isDefaultTypeSelected.collectAsState().value) {
        ListByIdListView(items)
    }
    else {
        ListByGroupListView(listItemViewModel.getGroupedData(),onListByIdCardClicked)
    }
}









