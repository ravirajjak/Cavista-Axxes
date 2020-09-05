package com.appturbo.cavista.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.appturbo.cavista.R
import com.appturbo.cavista.databinding.LayoutItemGridBinding
import com.appturbo.cavista.interfaces.OnClickRecylerviewItemListener
import com.appturbo.cavista.repository.model.response.Data
import com.appturbo.cavista.utility.Util

class CardAdapter(
    val context: Context,
    val iOnClickRecylerviewItemListener: OnClickRecylerviewItemListener
) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    lateinit var mDataList: ArrayList<Data>
    val mUtil by lazy {
        Util()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<LayoutItemGridBinding>(
            layoutInflater,
            R.layout.layout_item_grid,
            parent,
            false
        )
        return MyViewHolder(binding)

    }

    fun setData(mDataList: List<Data>) {
        this.mDataList = mDataList as ArrayList<Data>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (!::mDataList.isInitialized)
            mDataList = ArrayList()
        return mDataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        onBind(holder, position)
    }

    private fun onBind(holder: MyViewHolder, position: Int) {
        holder.binding.data = mDataList.get(position)
        val mImgView = holder.binding.layItemGridImgDisplay
        val mImageList = mDataList.get(position).images
        if (mImageList != null) {
            if (mImageList.size != 0) {
                val mImgUrl = mImageList.get(0)
                mUtil.loadImage(context, mImgView, mDataList.get(position).images.get(0).link)
            }
        }
        holder.binding.layItemGridCard.setOnClickListener {
            iOnClickRecylerviewItemListener.onClickRecylerItem(mDataList.get(position))
        }
    }


    class MyViewHolder(val binding: LayoutItemGridBinding) : RecyclerView.ViewHolder(binding.root)
}