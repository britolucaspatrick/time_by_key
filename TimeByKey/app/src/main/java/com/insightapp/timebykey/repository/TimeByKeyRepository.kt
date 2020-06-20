package com.insightapp.timebykey.repository

import androidx.lifecycle.LiveData
import com.insightapp.timebykey.dao.TimeByKeyDao
import com.insightapp.timebykey.entity.TimeByKey

class TimeByKeyRepository(
    private var timeByKeyDao: TimeByKeyDao
) {

    val allTimeByKey: LiveData<List<TimeByKey>> = timeByKeyDao.getAll()

    suspend fun insert(timeByKey: TimeByKey) {
        timeByKeyDao.insert(timeByKey)
    }

    suspend fun cancel(id: Int){
        timeByKeyDao.cancel(id)
    }

    suspend fun update(timeByKey: TimeByKey) {
        timeByKeyDao.update(timeByKey)
    }

    suspend fun allTimesByParamKey(key: String): List<TimeByKey>{
        return timeByKeyDao.allTimeByKey(key)
    }

}