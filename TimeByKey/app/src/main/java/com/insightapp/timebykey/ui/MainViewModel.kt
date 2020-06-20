package com.insightapp.timebykey.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.insightapp.timebykey.dao.AppRoomDatabase
import com.insightapp.timebykey.dao.TimeByKeyDao
import com.insightapp.timebykey.entity.TimeByKey
import com.insightapp.timebykey.repository.TimeByKeyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private var dao: TimeByKeyDao
    val allTimeByKey: LiveData<List<TimeByKey>>
    private var repository: TimeByKeyRepository

    init {
        dao = AppRoomDatabase.getDatabase(application).timeByKeyDao()
        repository = TimeByKeyRepository(dao)
        allTimeByKey = repository.allTimeByKey
    }

    fun insert(categoria: TimeByKey) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(categoria)
    }

    fun update(categoria: TimeByKey) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(categoria)
    }

    fun cancel(id: Int) = viewModelScope.launch(Dispatchers.IO){
        repository.cancel(id)
    }

    suspend fun getHoursByKey(key: String) : String {
        //get all times by key
        val list:List<TimeByKey> = repository.allTimesByParamKey(key)
        var hours:Long = 0
        Log.v("count_list", list.size.toString())

        list.forEach { time ->
            //validate whether the start and end date was informed
            if (time.inicio != null && time.fim != null){

                //subtract end with start time, after increment on variable hours
                val diff: Long = time.fim!!.time - time.inicio!!.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                hours += minutes / 60
            }
        }

        return "Key: ${key}, total em horas: ${hours}"
    }
}