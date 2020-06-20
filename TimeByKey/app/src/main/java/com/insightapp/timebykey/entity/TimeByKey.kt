package com.insightapp.timebykey.entity

import androidx.room.*
import java.util.*


@Entity(tableName = "TimeByKey")
@TypeConverters(Converters::class)
data class TimeByKey(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "key_time") var key_time: String,
    @ColumnInfo(name = "inicio") var inicio: Date?,
    @ColumnInfo(name = "fim") var fim: Date?,
    @ColumnInfo(name = "st_registro") var st_registro: String
)

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}