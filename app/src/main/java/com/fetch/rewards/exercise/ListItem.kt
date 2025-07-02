package com.fetch.rewards.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_item")
data class ListItem(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val listId : Int,
    val name : String?
)
