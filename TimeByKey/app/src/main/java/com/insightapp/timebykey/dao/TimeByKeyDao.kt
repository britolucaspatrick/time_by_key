package com.insightapp.timebykey.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.insightapp.timebykey.entity.TimeByKey


@Dao
interface TimeByKeyDao {

    @Query("SELECT * from timebykey WHERE st_registro != 'C'")
    fun getAll(): LiveData<List<TimeByKey>>

    @Query("SELECT * from timebykey WHERE st_registro != 'C'")
    fun getAllD(): LiveData<List<TimeByKey>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(timebykey: TimeByKey)

    @Query("DELETE FROM timebykey")
    suspend fun deleteAll()

    @Query("UPDATE timebykey SET st_registro = ${"'C'"} WHERE id = :id")
    suspend fun cancel(id: Int)

    @Update
    suspend fun update(categoria: TimeByKey)

}