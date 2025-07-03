package com.fetch.rewards.exercise.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fetch.rewards.exercise.db.ListItem

@Dao
interface ListItemDao {

    @Query("Select * from list_item order by listId ASC, name ASC")
    fun getAllListItems() : List<ListItem>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(items : List<ListItem>)


}