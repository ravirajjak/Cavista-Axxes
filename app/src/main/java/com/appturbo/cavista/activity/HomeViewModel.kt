package com.appturbo.cavista.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    val title = MutableLiveData<String>()
    val imgUrl = MutableLiveData<String>()
    val comment = MutableLiveData<String>()
    val cid = MutableLiveData<String>()

    fun setDetails(title: String, imgUrl: String, comment: String, cid: String) {
        this.title.value = title
        this.imgUrl.value = imgUrl
        this.comment.value = comment
        this.cid.value = cid
    }

    fun getImageUrl(): LiveData<String> {
        return imgUrl
    }

    fun getComment(): LiveData<String> {
        return comment
    }

}