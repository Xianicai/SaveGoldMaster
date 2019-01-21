package com.savegoldmaster.home.view.adapter

import android.content.Context
import com.example.zhanglibin.bdmoe.adapter.CommonRecyclerAdapter
import com.example.zhanglibin.bdmoe.adapter.MultiTypeSupport
import com.example.zhanglibin.bdmoe.adapter.ViewHolder
import com.savegoldmaster.R
import kotlinx.android.synthetic.main.mine_list_item.view.*

class MineListAdapter(context: Context, datas: ArrayList<MineListBean>) :
    CommonRecyclerAdapter<MineListBean>(context, datas, R.layout.mine_list_item) {
    override fun conver(holder: ViewHolder, item: MineListBean) {
        holder.setText(R.id.mTvTitle,item.title)
        holder.itemView.mImageCover.setImageResource(item.image)
    }
}

class MineListBean(var title: String, var image: Int, var id: Int)