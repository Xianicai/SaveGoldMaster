package com.savegoldmaster.home.view.adapter

import android.content.Context
import com.example.zhanglibin.bdmoe.adapter.CommonRecyclerAdapter
import com.example.zhanglibin.bdmoe.adapter.ViewHolder
import com.savegoldmaster.R
import com.savegoldmaster.home.model.bean.NearbyShopBean
import kotlinx.android.synthetic.main.home_nearby_shop_item.view.*
import kotlinx.android.synthetic.main.mine_list_item.view.*

class HomeNearbyShopAdapter(context: Context, datas: ArrayList<NearbyShopBean.ContentBean>) :
    CommonRecyclerAdapter<NearbyShopBean.ContentBean>(context, datas, R.layout.home_nearby_shop_item) {
    override fun conver(holder: ViewHolder, item: NearbyShopBean.ContentBean) {
        holder.setText(R.id.mTvShopName, item.name)
        holder.setText(R.id.mTvLabel, item.name)
        //店铺状态 0 离我最近 1回收最多 2评价最高
//        holder.itemView.mTvLabel.text =
        when (item.status) {
            0 -> {
                holder.itemView.mTvLabel.text = "离我最近"
            }
            1 -> {
                holder.itemView.mTvLabel.text = "回收最多"
            }
            2 -> {
                holder.itemView.mTvLabel.text = "评价最高"
            }
        }
        holder.itemView.mTvShopCover.setImage(item.url)
    }
}