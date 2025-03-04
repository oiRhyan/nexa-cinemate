package com.nexa.cinemate.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexa.cinemate.data.database.entitys.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Query("SELECT * FROM MovieEntity")
    fun findAllFavoriteMovies() : Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie : MovieEntity)

}