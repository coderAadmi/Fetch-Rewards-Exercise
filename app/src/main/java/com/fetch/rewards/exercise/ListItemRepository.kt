package com.fetch.rewards.exercise

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListItemRepository @Inject constructor(private val api: ListApi, private val listItemDao: ListItemDao) {
    fun getAllListItems() : Flow<Result<List<ListItem>>> = flow{
        emit(Result.Loading)
        try {
            Log.d("RES_CALL","Method called")
            getDataFromNetwork()
        }
        catch (e : Exception){
            Log.d("RES_CALL_E",e.toString())
            emit(Result.Failure(e))
        }
        val localData = withContext(Dispatchers.IO) {
            listItemDao.getAllListItems()
        }
        emit(Result.Success(localData))

    }

    suspend fun getDataFromNetwork(){

        Log.d("RES_CALL_","Invoked")
        val response = api.getListItems()
        Log.d("RES_CALL","DONE")
        if (response.isSuccessful) {
            Log.d("RES_CALL_S",response.body().toString())
            listItemDao.insert(response.body()!!)
        }
        else{
            Log.d("RES_CALL", "response status "+response.message())
            throw Exception("API error occurred")
        }
    }


}