package com.appturbo.cavista.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appturbo.cavista.database.interfaces.ImageDao
import com.appturbo.cavista.database.table.ImageInfo


@Database(entities = arrayOf(ImageInfo::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getImageDao(): ImageDao
}
