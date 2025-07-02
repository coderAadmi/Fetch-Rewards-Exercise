package com.fetch.rewards.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ListItemDao {

    @Query("Select * from list_item order by listId ASC, name ASC")
    fun getAllListItems() : List<ListItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items : List<ListItem>)


}