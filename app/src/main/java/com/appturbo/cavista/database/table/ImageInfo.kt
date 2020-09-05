package com.appturbo.cavista.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_info")
data class ImageInfo(

    @PrimaryKey(autoGenerate = true) val id: Int =0,
    @ColumnInfo(name = "cid") var cid: String?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "comment") var comment: String?
)