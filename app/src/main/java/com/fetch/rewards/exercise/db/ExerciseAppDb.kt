package com.fetch.rewards.exercise.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ListItem::class], version = 4)
abstract class ExerciseAppDb : RoomDatabase() {
    abstract fun listItemDao() : ListItemDao
}