package com.asyfdzaky.uas_pppb_fitnes_app.databaseLokal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.asyfdzaky.uas_pppb_fitnes_app.Model.Room.LatihanTable

@Dao
interface LatihanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(latihan : LatihanTable)
    @Delete
    fun delete(latihan : LatihanTable)
    @Query("SELECT * from latihan_table")
    fun getAllLatihan (): LiveData<List<LatihanTable>>
}