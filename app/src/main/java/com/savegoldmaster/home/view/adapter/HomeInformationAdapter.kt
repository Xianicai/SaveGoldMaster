package com.savegoldmaster.home.view.adapter

import android.content.Context
import com.savegoldmaster.R
import com.savegoldmaster.common.WebUrls
import com.savegoldmaster.home.model.bean.InformationBean
import com.savegoldmaster.utils.adapter.CommonRecyclerAdapter
import com.savegoldmaster.utils.adapter.ViewHolder
import com.savegoldmaster.utils.webutil.OutWebActivity
import kotlinx.android.synthetic.main.home_information_item.view.*

class HomeInformationAdapter(context: Context, datas: ArrayList<InformationBean.ContentBean.ListBean>) :
    CommonRecyclerAdapter<InformationBean.ContentBean.ListBean>(context, datas, R.layout.home_information_item) {
    override fun conver(holder: ViewHolder, item: InformationBean.ContentBean.ListBean) {
        holder.setText(R.id.mTvTitle, item.title)
        holder.setText(R.id.mTvTime, item.createTime.substring(5, 10))
        holder.itemView.mImageCover.setImage(item.imageUrl)
        holder.itemView.setOnClickListener {
            OutWebActivity.start(holder.itemView.context, WebUrls.NEWS_DETAIL + item.id)
        }
    }
}