package com.savegoldmaster.home.view.adapter

import android.content.Context
import com.savegoldmaster.R
import com.savegoldmaster.utils.adapter.CommonRecyclerAdapter
import com.savegoldmaster.utils.adapter.ViewHolder
import kotlinx.android.synthetic.main.mine_list_item.view.*

class MineListAdapter(context: Context, datas: ArrayList<MineListBean>) :
    CommonRecyclerAdapter<MineListBean>(context, datas, R.layout.mine_list_item) {
    override fun conver(holder: ViewHolder, item: MineListBean) {
        holder.setText(R.id.mTvTitle,item.title)
        holder.itemView.mImageCover.setImageResource(item.image)
    }
}

class MineListBean(var title: String, var image: Int, var id: Int)