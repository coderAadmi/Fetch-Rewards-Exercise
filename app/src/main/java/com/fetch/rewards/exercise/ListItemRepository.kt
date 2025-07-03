package com.fetch.rewards.exercise

import android.util.Log
import com.fetch.rewards.exercise.db.ListItem
import com.fetch.rewards.exercise.db.ListItemDao
import com.fetch.rewards.exercise.network.ApiResultFailure
import com.fetch.rewards.exercise.network.ConnectivityService
import com.fetch.rewards.exercise.network.ListApi
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
import java.net.UnknownHostException
import javax.inject.Inject

class ListItemRepository @Inject constructor(
    private val api: ListApi,
    private val listItemDao: ListItemDao,
    private val connectivityService: ConnectivityService
) {
    fun getAllListItems(): Flow<Result<List<ListItem>>> = flow {
        emit(Result.Loading)
        try {
            if (getDataFromNetwork()) {
                val localData = withContext(Dispatchers.IO) {
                    listItemDao.getAllListItems()
                }
                emit(Result.Success(localData))
            }

        } catch (e: Exception) {
            Log.d("RES_CALL_E", e.toString())
            val localData = withContext(Dispatchers.IO) {
                listItemDao.getAllListItems()
            }
            if (localData.isNullOrEmpty()) {
                emit(Result.Failure(e))
            } else
                emit(Result.OfflineSucces(localData))
        }


    }

    suspend fun getDataFromNetwork(): Boolean {
        Log.d("RES_CALL_", "Invoked")
        val response = api.getListItems()
        Log.d("RES_CALL", "DONE")
        if (response.isSuccessful) {
            Log.d("RES_CALL_S", response.body().toString())
            listItemDao.insert(response.body()!!)
            return true
        } else {
            Log.d("RES_CALL", "response status " + response.message())
            throw Exception("API error occurred")
        }
        return false
    }


    private val _searchState = MutableStateFlow<String>("")
    val searchState: StateFlow<String> = _searchState.asStateFlow()

    private val _isFilterActive = MutableStateFlow<Boolean>(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive.asStateFlow()

    private val _isDefaultTypeSelected = MutableStateFlow<Boolean>(false)
    val isDefaultTypeSelected: StateFlow<Boolean> = _isDefaultTypeSelected.asStateFlow()

    private val _listState = MutableStateFlow<List<ListItem>>(emptyList())
    val listState: StateFlow<List<ListItem>> = _listState.asStateFlow()

    private val _apiResult = MutableStateFlow<ApiResultFailure>(ApiResultFailure.Unknown)
    val apiResult: StateFlow<ApiResultFailure> = _apiResult.asStateFlow()

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
            if (!isFilterActive.value)
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
                            _apiResult.value = ApiResultFailure.Loading
                        }

                        is Result.Success -> {
                            Log.d("RES_CALL", "Sucess")
                            _apiResult.value = ApiResultFailure.Success
                            _listState.value = result.data.filter { !it.name.isNullOrBlank() }
                        }

                        is Result.Failure -> {
                            if (result.e is UnknownHostException) {
                                _apiResult.value = ApiResultFailure.NoInternetConnection
                            } else {
                                _apiResult.value = ApiResultFailure.Error
                            }
                        }

                        is Result.OfflineSucces -> {
                            _apiResult.value = ApiResultFailure.Error
                            _listState.value = result.data.filter { !it.name.isNullOrBlank() }
                        }
                    }
                }.launchIn(this)
        }
    }
}