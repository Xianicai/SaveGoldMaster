package com.savegoldmaster.home.view.adapter

import android.content.Context
import com.example.zhanglibin.bdmoe.adapter.CommonRecyclerAdapter
import com.example.zhanglibin.bdmoe.adapter.ViewHolder
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.home.model.bean.NearbyShopBean
import kotlinx.android.synthetic.main.home_nearby_shop_item.view.*
import kotlinx.android.synthetic.main.mine_list_item.view.*

class HomeNearbyShopAdapter(context: Context, datas: ArrayList<NearbyShopBean.ContentBean>) :
    CommonRecyclerAdapter<NearbyShopBean.ContentBean>(context, datas, R.layout.home_nearby_shop_item) {
    override fun conver(holder: ViewHolder, item: NearbyShopBean.ContentBean) {
        holder.setText(R.id.mTvShopName, item.name)
        holder.itemView.mTvShopCover.setImage(item.url)
    }
}