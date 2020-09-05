package com.appturbo.cavista.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.appturbo.cavista.R
import com.appturbo.cavista.adapter.CardAdapter
import com.appturbo.cavista.databinding.ActivityMainBinding
import com.appturbo.cavista.interfaces.OnClickRecylerviewItemListener
import com.appturbo.cavista.repository.model.response.Data
import com.appturbo.cavista.utility.AppConstant
import com.appturbo.cavista.utility.SpacesItemDecoration
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivity : BaseActivity(), OnClickRecylerviewItemListener {

    val TAG = HomeActivity::class.java.simpleName
    lateinit var binding: ActivityMainBinding
    lateinit var mDataList: List<Data>
    val mAuthorization = "Client-ID 137cda6b5008a7c"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        setOnClickListener()
        setOnSwipeRefreshListener()
        setOnEditTextQueryListener()

    }


    private fun setOnEditTextQueryListener() {
        binding.incAppHeader.incAhSearchview.setOnQueryTextListener(object :
            MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty())
                    if (query.length > 3)
                        getData(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

    }

    private fun setOnSwipeRefreshListener() {
        binding.actMainSwiperefresh.setOnRefreshListener {
            binding.actMainSwiperefresh.isRefreshing = false
        }
    }

    private fun setOnClickListener() {
        val mSearchView = binding.incAppHeader.incAhSearchview
        mSearchView.setOnClickListener {
            if (!mSearchView.isSearchOpen)
                mSearchView.showSearch(true)
        }
    }

    private fun getData(text: String) {

        showProgress()
        lifecycleScope.launch(Dispatchers.IO) {
            val mResponse = mApiService.getHomeData(mAuthorization, text)

            lifecycleScope.launch(Dispatchers.Main) {
                hideProgress()
                if (mResponse.isSuccessful) {
                    val mHomePResponse = mResponse.body()
                    onSuccess(mHomePResponse?.data)
                } else {
                    Log.d(TAG, "Failure")
                }
            }
        }
    }

    private fun onSuccess(mDataList: List<Data>?) {

        binding.actMainRecylerview.layoutManager = GridLayoutManager(this, 4)
        val mAdapter = CardAdapter(this, this)
        binding.actMainRecylerview.adapter = mAdapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.margin_1)
        binding.actMainRecylerview.addItemDecoration(SpacesItemDecoration(spacingInPixels))
        mDataList?.let { mAdapter.setData(it) }
        if (mDataList != null) {
            this.mDataList = mDataList
        }

    }


    private fun openIntent(mClass: Class<*>, bundle: Bundle) {
        val intent = Intent(this, mClass).apply {
            putExtra(AppConstant.PE_DETAILS, bundle)
        }
        startActivity(intent)
    }

    override fun onClickRecylerItem(data: Any) {
        if (data is Data) {
            val bundle = Bundle().apply {
                putString(AppConstant.PE_TITLE, data.title)
                putString(AppConstant.PE_IMAGE_URL, mUtility.getImageUrl(data.images))
                putString(AppConstant.PE_ID, data.id)
            }
            openIntent(newInstance(ImageDetailActivity::class.java), bundle)
        }
    }
}