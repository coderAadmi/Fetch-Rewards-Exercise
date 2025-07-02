package com.fetch.rewards.exercise

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ListApi {

    @GET("hiring.json")
    suspend fun getListItems() : Response<List<ListItem>>
}