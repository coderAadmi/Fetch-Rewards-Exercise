package com.fetch.rewards.exercise


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetch.rewards.exercise.db.ListItem
import com.fetch.rewards.exercise.network.ApiResultFailure
import com.fetch.rewards.exercise.network.ConnectivityService
import com.fetch.rewards.exercise.network.ConnectivityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(private val listItemRepository: ListItemRepository,
    private val connectivityService: ConnectivityService
) : ViewModel(){

    val searchState : StateFlow<String> = listItemRepository.searchState

    val isFilterActive : StateFlow<Boolean> = listItemRepository.isFilterActive

    val isDefaultTypeSelected : StateFlow<Boolean> = listItemRepository.isDefaultTypeSelected

    val listState : StateFlow<List<ListItem>> = listItemRepository.listState

    val filteredListState : StateFlow<List<ListItem>> = listItemRepository.filteredListState

    val apiResultFailure : StateFlow<ApiResultFailure> = listItemRepository.apiResult

    init {
            getListItems()

    }

    fun refresh(){
        getListItems()
    }

    private fun getListItems(){
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

    fun getConnectionStatus() = connectivityService.connectionStatus.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(5000), ConnectivityStatus.Unknown)



}