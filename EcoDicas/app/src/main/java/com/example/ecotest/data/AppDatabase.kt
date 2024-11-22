package com.example.ecotest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecotest.model.TipsModel

@Database(entities = [TipsModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tipDAO(): TipsDAO
}