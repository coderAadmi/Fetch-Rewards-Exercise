package com.fetch.rewards.exercise.network

import com.fetch.rewards.exercise.db.ListItem
import retrofit2.Response
import retrofit2.http.GET

interface ListApi {

    @GET("hiring.json")
    suspend fun getListItems() : Response<List<ListItem>>
}