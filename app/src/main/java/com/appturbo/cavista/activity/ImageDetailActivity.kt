package com.appturbo.cavista.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.appturbo.cavista.R
import com.appturbo.cavista.database.table.ImageInfo
import com.appturbo.cavista.databinding.ActivityDetailImageBinding
import com.appturbo.cavista.utility.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ImageDetailActivity : BaseActivity() {

    lateinit var binding: ActivityDetailImageBinding
    lateinit var mHomeModel: HomeViewModel
    lateinit var mBundleData: Bundle
    lateinit var mCID: String
    lateinit var mTitle: String
    lateinit var mImgUrl: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_image)
        getIntentData()
        init()
    }

    private fun getIntentData() {
        intent?.let {
            mBundleData = it.getBundleExtra(AppConstant.PE_DETAILS)!!
        }
    }

    private fun init() {
//        setViewModel()
        setOnClickListener()
        setData()
    }

    private fun setData() {
        mTitle = mBundleData.getString(AppConstant.PE_TITLE)!!
        mImgUrl = mBundleData.getString(AppConstant.PE_IMAGE_URL)!!
        mCID = mBundleData.getString(AppConstant.PE_ID)!!

        if (mImgUrl.isNotEmpty()) {
            mUtility.loadImage(applicationContext, binding.actDiImgDisplay, mImgUrl)
        }
        lifecycleScope.launch(Dispatchers.IO)
        {
            val mData = getImageInfo()
            lifecycleScope.launch(Dispatchers.Main) {
                mData?.let {
                    binding.actDiEtComment.setText(mData.comment)
                }
            }
        }
    }

    private fun getImageInfo(): ImageInfo {
        return db.getImageDao().getImageInfo(mCID)
    }

/*
    private fun setObservable() {
        mHomeModel.getImageUrl().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        mHomeModel.getComment().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        })
    }
*/


/* private fun setViewModel() {
     mHomeModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
 }*/

    private fun setOnClickListener() {
        binding.actDiBtnComment.setOnClickListener {
            val mComment = binding.actDiEtComment.text.toString()
            lifecycleScope.launch(Dispatchers.IO) {
                val mData = getImageInfo()
                if (mData != null) {
                    val mStoredComment = mData.comment
                    if (mStoredComment != null) {
                        db.getImageDao().updateImageInfo(mComment, mCID)
                    } else {
                        db.getImageDao().insertImageInfo(ImageInfo(0, mCID, mTitle, mComment))
                    }
                } else {
                    db.getImageDao().insertImageInfo(ImageInfo(0, mCID, mTitle, mComment))
                }
            }
        }
    }
}