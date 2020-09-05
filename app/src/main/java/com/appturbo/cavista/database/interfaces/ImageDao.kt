package com.appturbo.cavista.database.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.appturbo.cavista.database.table.ImageInfo

@Dao
interface ImageDao {

    @Query("SELECT * FROM  image_info  WHERE cid LIKE (:cid)")
    fun getImageInfo(cid: String): ImageInfo

    @Insert
    fun insertImageInfo(imgInfo: ImageInfo)

    @Query("UPDATE  image_info SET comment = :comment WHERE cid =:cid")
    fun updateImageInfo(comment: String, cid: String)


}