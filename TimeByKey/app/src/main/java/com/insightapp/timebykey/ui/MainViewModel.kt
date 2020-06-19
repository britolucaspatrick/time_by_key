package com.insightapp.timebykey.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.insightapp.timebykey.dao.AppRoomDatabase
import com.insightapp.timebykey.entity.TimeByKey
import com.insightapp.timebykey.repository.TimeByKeyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    val allTimeByKey: LiveData<List<TimeByKey>>
    private val repository: TimeByKeyRepository

    init {
        val dao = AppRoomDatabase.getDatabase(application).timeByKeyDao()
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
}