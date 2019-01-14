package com.savegoldmaster.home.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import com.example.zhanglibin.bdmoe.adapter.CommonRecyclerAdapter
import com.example.zhanglibin.bdmoe.adapter.MultiTypeSupport
import com.example.zhanglibin.bdmoe.adapter.ViewHolder
import kotlinx.android.synthetic.main.layout_home_banner.view.*
import com.savegoldmaster.utils.glide.GlideImageView
import com.savegoldmaster.utils.ToastUtil
import com.stx.xhb.xbanner.XBanner
import com.savegoldmaster.home.view.MainActivity
import com.bumptech.glide.Glide
import com.example.zhanglibin.savegoldmaster.R
import com.example.zhanglibin.savegoldmaster.R.id.mRecyclerView
import com.savegoldmaster.home.model.bean.*
import kotlinx.android.synthetic.main.layout_home_gold_price.view.*
import kotlinx.android.synthetic.main.layout_home_nearby_shop.view.*
import kotlinx.android.synthetic.main.layout_home_notice.view.*
import kotlinx.android.synthetic.main.layout_home_order_list.view.*
import kotlinx.android.synthetic.main.layout_order_text.view.*
import kotlinx.android.synthetic.main.layout_total_recycle.view.*
import android.widget.TextView


class HomeAdapter(context: Context, private var datas: ArrayList<Object>) :
    CommonRecyclerAdapter<Object>(context, datas, object : MultiTypeSupport<Object> {
        override fun getLayoutId(item: Object, position: Int): Int {
            return when (position) {
                TYPE_HOME_BANNER -> R.layout.layout_home_banner
                TYPE_HOME_NOTICE -> R.layout.layout_home_notice
                TYPE_HOME_GOLD_PRICE -> R.layout.layout_home_gold_price
                TYPE_HOME_ORDER_LIST -> R.layout.layout_home_order_list
                TYPE_HOME_NEARBY_SHOP -> R.layout.layout_total_recycle
                TYPE_HOME_INFORMATION -> R.layout.layout_home_nearby_shop
                else -> R.layout.layout_home_nearby_shop
            }
        }
    }) {
    companion object {
        val TYPE_HOME_BANNER: Int = 0
        val TYPE_HOME_NOTICE: Int = 1
        val TYPE_HOME_GOLD_PRICE: Int = 2
        val TYPE_HOME_ORDER_LIST: Int = 3
        val TYPE_HOME_NEARBY_SHOP: Int = 4
        val TYPE_HOME_INFORMATION: Int = 5
    }

    private var showNoticeView: Boolean = true
    fun setShowNoticeView(showNoticeView: Boolean) {
        this.showNoticeView = showNoticeView
        notifyDataSetChanged()
    }

    override fun conver(holder: ViewHolder, item: Object) {
        when (holder.adapterPosition) {
            TYPE_HOME_BANNER -> {
                datas.forEach {
                    if (it is BannerBean) {
                        buildBanner(it, holder)
                    }
                }
            }
            TYPE_HOME_NOTICE -> {
                datas.forEach {
                    if (it is NoticeBean.ContentBean.ListBean) {
                        buildNotice(it, holder)
                    }
                }
            }
            TYPE_HOME_GOLD_PRICE -> {
                datas.forEach {
                    if (it is GoldPriceBean.ContentBean) {
                        buildGoldPrice(it, holder)
                    }
                }

            }
            TYPE_HOME_ORDER_LIST -> {
                datas.forEach {
                    if (it is UserOderBean) {
                        buildOrderMsg(it, holder)
                    }
                }
            }
            TYPE_HOME_NEARBY_SHOP -> {
                datas.forEach {
                    if (it is RecyclerGoldBean) {
                        buildRecyclerGold(it, holder)
                    }
                }
            }
            TYPE_HOME_INFORMATION -> {
                datas.forEach {
                    if (it is NearbyShopBean) {
                        buildShopList(it, holder)
                    }
                }
            }
            else -> {
                datas.forEach {
                    if (it is InformationBean) {
                        buildInfomationList(it, holder)
                    }
                }
            }
        }
    }

    private fun buildInfomationList(informationBean: InformationBean, holder: ViewHolder) {
        holder.itemView.mTvTitle.apply {
            text = "黄金资讯"
            paint.isFakeBoldText = true
        }
        holder.itemView.mTvInfo.text = "为您提供最新 有价值的黄金领域资讯"
        holder.itemView.setOnClickListener { }
        holder.itemView.mRecyclerView.apply {
            layoutManager = LinearLayoutManager(holder.itemView.context)
            adapter = HomeInformationAdapter(
                holder.itemView.context,
                informationBean.content.list as ArrayList<InformationBean.ContentBean.ListBean>
            )
        }
    }

    private fun buildShopList(nearbyShopBean: NearbyShopBean, holder: ViewHolder) {
        holder.itemView.mTvTitle.apply {
            text = "附近店铺"
            paint.isFakeBoldText = true
        }
        holder.itemView.mTvInfo.text = "前往附近店铺进行黄金回收"
        holder.itemView.setOnClickListener { }
        holder.itemView.mRecyclerView.apply {
            layoutManager = GridLayoutManager(holder.itemView.context, 3)
            adapter = HomeNearbyShopAdapter(
                holder.itemView.context,
                nearbyShopBean.content as ArrayList<NearbyShopBean.ContentBean>
            )
        }
    }

    private fun buildRecyclerGold(recyclerGoldBean: RecyclerGoldBean, holder: ViewHolder) {
        holder.itemView.mTvTotalRecycle.text = recyclerGoldBean.content.weight.toString()
    }

    private fun buildOrderMsg(userOderBean: UserOderBean, holder: ViewHolder) {
//    holder.itemView.mTvOrderMsg.text = "${userOder.createTime},${userOder.username}的黄金估计${userOder.amount}"
        val flipperViews = ArrayList<View>()
        for (i in 0 until userOderBean.content.size) {
            val contentBean = userOderBean.content[i]
            val view = LayoutInflater.from(holder.itemView.context).inflate(R.layout.layout_order_text, null, false)
            view.mTvOrderMsg.text = "${contentBean.createTime},${contentBean.username}的黄金估计${contentBean.amount}"
            flipperViews.add(view)
        }
        holder.itemView.pushUpFlipper.apply {
            removeAllViews()
            flipperViews.forEach {
                addView(it)
            }

            startFlipping()
            setFlipInterval(2000)
            inAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scroll_in);
            outAnimation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scroll_out);
        }


    }

    private fun buildGoldPrice(contentBean: GoldPriceBean.ContentBean, holder: ViewHolder) {
        holder.itemView.mTvGoldTrend.setOnClickListener {

        }
        holder.itemView.mTvBuyGold.setOnClickListener {

        }
        holder.itemView.mTvRecycleGold.paint.isFakeBoldText = true
        val priceText = "${contentBean.goldPrice}元/克"
        holder.itemView.mTvGoldPrice.text = SpannableString(priceText).apply {
            setSpan(RelativeSizeSpan(3.33f), 0, priceText.length - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(StyleSpan(Typeface.BOLD), 0, priceText.length - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun buildNotice(noticeBean: NoticeBean.ContentBean.ListBean, holder: ViewHolder) {
        holder.itemView.mImageClose.setOnClickListener {
            setShowNoticeView(false)
        }
        holder.itemView.mTvNotice.apply {
            text = noticeBean.content
            isSelected = true
        }
    }

    private fun buildBanner(bannerBean: BannerBean, holder: ViewHolder) {
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
