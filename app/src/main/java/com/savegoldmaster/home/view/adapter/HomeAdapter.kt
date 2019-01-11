package com.savegoldmaster.home.view.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.example.zhanglibin.bdmoe.adapter.CommonRecyclerAdapter
import com.example.zhanglibin.bdmoe.adapter.MultiTypeSupport
import com.example.zhanglibin.bdmoe.adapter.ViewHolder
import com.savegoldmaster.home.model.bean.BannerBean
import kotlinx.android.synthetic.main.layout_home_banner.view.*
import com.savegoldmaster.utils.glide.GlideImageView
import com.savegoldmaster.home.model.bean.XBannerBean
import com.savegoldmaster.utils.ToastUtil
import com.stx.xhb.xbanner.XBanner
import com.savegoldmaster.home.view.MainActivity
import com.bumptech.glide.Glide
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.home.model.bean.NoticeBean
import kotlinx.android.synthetic.main.layout_home_notice.view.*


class HomeAdapter(context: Context, datas: ArrayList<Object>) :
    CommonRecyclerAdapter<Object>(context, datas, object : MultiTypeSupport<Object> {
        override fun getLayoutId(item: Object, position: Int): Int {
            return when (position) {
                0 -> R.layout.layout_home_banner
                1 -> R.layout.layout_home_notice
                else -> R.layout.layout_home_banner
            }
        }
    }) {
    override fun conver(holder: ViewHolder, item: Object) {
        when (holder.adapterPosition) {
            0 -> {
                buildBanner(item, holder)
            }
            1 -> {
                val noticeBean = item as NoticeBean.ContentBean.ListBean
                holder.itemView.mTvNotice.apply {
                    text = noticeBean.content
                    isSelected = true
                }
            }
        }
    }

    private fun buildBanner(item: Object, holder: ViewHolder) {
        val bannerBean = item as BannerBean
        val views = mutableListOf<GlideImageView>()
        val imageUrls = mutableListOf<XBannerBean>()
        for (i in 0 until bannerBean.content.size) {
            val mImageView = GlideImageView(holder.itemView.context)
            mImageView.setDefaultImage(R.mipmap.ic_launcher)
            mImageView.scaleType = ImageView.ScaleType.FIT_XY
            mImageView.setImage(bannerBean.content[i].imgUrl)
            imageUrls.add(XBannerBean(bannerBean.content[i].imgUrl))
            views.add(mImageView)
        }

        holder.itemView.banner.apply {
            setBannerData(imageUrls)
            loadImage { banner, model, view, position ->
                Glide.with(holder.itemView.context).load((model as XBannerBean).imagerUrls)
                    .into(view as ImageView)
            }
            setOnItemClickListener { banner, model, view, position ->
                ToastUtil.showMessage("点击了第" + position + "图片")
            }
        }
    }
}