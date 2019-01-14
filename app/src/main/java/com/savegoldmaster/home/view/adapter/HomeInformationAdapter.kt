package com.savegoldmaster.home.view.adapter

import android.content.Context
import com.example.zhanglibin.bdmoe.adapter.CommonRecyclerAdapter
import com.example.zhanglibin.bdmoe.adapter.ViewHolder
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.home.model.bean.InformationBean
import com.savegoldmaster.home.model.bean.NearbyShopBean
import kotlinx.android.synthetic.main.home_information_item.view.*

class HomeInformationAdapter(context: Context, datas: ArrayList<InformationBean.ContentBean.ListBean>) :
    CommonRecyclerAdapter<InformationBean.ContentBean.ListBean>(context, datas, R.layout.home_information_item) {
    override fun conver(holder: ViewHolder, item: InformationBean.ContentBean.ListBean) {
        holder.setText(R.id.mTvTitle, item.title)
        holder.setText(R.id.mTvTime, item.createTime)
        holder.itemView.mImageCover.setImage(item.imageUrl)
    }
}