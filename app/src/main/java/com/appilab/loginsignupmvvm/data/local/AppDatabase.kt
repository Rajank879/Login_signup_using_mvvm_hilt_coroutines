package com.appilab.loginsignupmvvm.data.local

import androidx.room.RoomDatabase

abstract class AppDatabase:RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    companion object{
        const val DATABASE_NAME = "app_db"
    }
}