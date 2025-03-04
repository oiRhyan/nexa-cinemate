package com.nexa.cinemate.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexa.cinemate.data.database.dao.MovieDAO
import com.nexa.cinemate.data.database.entitys.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class NexaDataBase : RoomDatabase() {
    abstract fun movieDao() : MovieDAO
}