package com.fetch.rewards.exercise

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListItemRepository @Inject constructor(private val api: ListApi, private val listItemDao: ListItemDao) {
    fun getAllListItems(): Flow<Result<List<ListItem>>> = flow {
        emit(Result.Loading)
        try {
            Log.d("RES_CALL", "Method called")
            getDataFromNetwork()
        } catch (e: Exception) {
            Log.d("RES_CALL_E", e.toString())
            emit(Result.Failure(e))
        }
        val localData = withContext(Dispatchers.IO) {
            listItemDao.getAllListItems()
        }
        emit(Result.Success(localData))

    }

    suspend fun getDataFromNetwork() {

        Log.d("RES_CALL_", "Invoked")
        val response = api.getListItems()
        Log.d("RES_CALL", "DONE")
        if (response.isSuccessful) {
            Log.d("RES_CALL_S", response.body().toString())
            listItemDao.insert(response.body()!!)
        } else {
            Log.d("RES_CALL", "response status " + response.message())
            throw Exception("API error occurred")
        }
    }


    private val _searchState = MutableStateFlow<String>("")
    val searchState: StateFlow<String> = _searchState.asStateFlow()

    private val _isFilterActive = MutableStateFlow<Boolean>(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()

    private val _isDefaultTypeSelected = MutableStateFlow<Boolean>(true)
    val isDefaultTypeSelected: StateFlow<Boolean> = _isDefaultTypeSelected.asStateFlow()

    private val _listState = MutableStateFlow<List<ListItem>>(emptyList())
    val listState: StateFlow<List<ListItem>> = _listState.asStateFlow()

    val _filteredListState: MutableStateFlow<List<ListItem>> =
        MutableStateFlow<List<ListItem>>(emptyList())
    val filteredListState: StateFlow<List<ListItem>> = _filteredListState.asStateFlow()

    fun editDefaultTypeSelected(defSelected: Boolean) {
        _isDefaultTypeSelected.value = defSelected
    }

    fun setFilterActiveStatus(isActive: Boolean) {
        _isFilterActive.value = isActive
        if (!isActive) {
            _filteredListState.value = emptyList<ListItem>()
        }
    }

    fun setSearchValue(value: String) {
        _searchState.value = value
        if (value.isNotEmpty() && value.length > 2) {
            if(!isFilterActive.value)
                setFilterActiveStatus(true)
            applyFilter()
        } else {
            if (isFilterActive.value)
                setFilterActiveStatus(false)
        }

    }

    fun applyFilter() {
        var data = emptyList<ListItem>()
        if (isFilterActive.value) {
            if (searchState.value.isNotEmpty() && searchState.value.length > 2) {
                data = listState.value.filter { item ->
                    item.name!!.contains(searchState.value)
                }
            }
            _filteredListState.value = data
        }
    }

    fun getListItems(viewModelScope: CoroutineScope) {
        viewModelScope.launch {

            getAllListItems()
                .onEach { result ->
                    when (result) {
                        is Result.Loading -> {
                            Log.d("RES_CALL", "Loading")
                        }

                        is Result.Success -> {
                            Log.d("RES_CALL", "Sucess")
                            Log.d("RES_CALL", result.data.get(0).toString())
                            _listState.value = result.data.filter { !it.name.isNullOrBlank() }
                        }

                        is Result.Failure -> {
                            Log.d("RES_CALL", "Failure")
                        }
                    }
                }.launchIn(this)
        }
    }
}