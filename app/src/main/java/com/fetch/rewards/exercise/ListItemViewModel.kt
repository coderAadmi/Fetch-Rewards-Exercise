package com.fetch.rewards.exercise

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.groupBy

@HiltViewModel
class ListItemViewModel @Inject constructor(private val listItemRepository: ListItemRepository) : ViewModel(){

    val searchState : StateFlow<String> = listItemRepository.searchState

    val isFilterActive : StateFlow<Boolean> = listItemRepository.isFilterActive

    val isDefaultTypeSelected : StateFlow<Boolean> = listItemRepository.isDefaultTypeSelected

    val listState : StateFlow<List<ListItem>> = listItemRepository.listState

    val filteredListState : StateFlow<List<ListItem>> = listItemRepository.filteredListState

    init {
        getListItems()
    }

    fun getListItems(){
        listItemRepository.getListItems(viewModelScope)
    }

    fun editDefaultTypeSelected(defSelected: Boolean) {
        listItemRepository.editDefaultTypeSelected(defSelected)
    }

    fun setSearchValue(value : String){
        listItemRepository.setSearchValue(value)
    }


    fun getFilteredList() : StateFlow<List<ListItem>> {
        return filteredListState
    }



    fun getGroupedData(): Map<Int, List<ListItem>> {
        if(isFilterActive.value){
            return getFilteredList().value.groupBy({ it.listId }, { it })
        }
        return listState.value
            .groupBy({ it.listId }, { it })
    }
}