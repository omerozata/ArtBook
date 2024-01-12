package com.kuantum.artbook.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertArt(art: Art)
    @Delete
    suspend fun deleteArt(art: Art)
    @Query("SELECT * FROM arts")
    fun getArts() : LiveData<List<Art>>

}