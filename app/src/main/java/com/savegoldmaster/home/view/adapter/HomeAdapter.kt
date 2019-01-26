package com.savegoldmaster.home.view.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.savegoldmaster.R
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_home_gold_price.view.*
import kotlinx.android.synthetic.main.layout_home_nearby_shop.view.*
import kotlinx.android.synthetic.main.layout_home_order_list.view.*
import kotlinx.android.synthetic.main.layout_order_text.view.*
import kotlinx.android.synthetic.main.layout_total_recycle.view.*

class HomeAdapter(private var datas: ArrayList<Object>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        val TYPE_HOME_GOLD_PRICE: Int = 0
        val TYPE_HOME_ORDER_LIST: Int = 1
        val TYPE_HOME_TOTAL_RECYCLE: Int = 2
        val TYPE_HOME_NEARBY_SHOP: Int = 3
        val TYPE_HOME_INFORMATION: Int = 4
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HomeAdapter.TYPE_HOME_GOLD_PRICE
            1 -> HomeAdapter.TYPE_HOME_ORDER_LIST
            2 -> HomeAdapter.TYPE_HOME_TOTAL_RECYCLE
            3 -> HomeAdapter.TYPE_HOME_NEARBY_SHOP
            else -> HomeAdapter.TYPE_HOME_INFORMATION
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        when (viewType) {
            HomeAdapter.TYPE_HOME_GOLD_PRICE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_gold_price, parent, false)
                return GoldPriceViewHolder(view)
            }
            HomeAdapter.TYPE_HOME_ORDER_LIST -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_order_list, parent, false)
                return OrderMsgViewHolder(view)
            }
            HomeAdapter.TYPE_HOME_TOTAL_RECYCLE -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.layout_total_recycle, parent, false)
                return TotalRecyclerGoldViewHolder(view)
            }
            HomeAdapter.TYPE_HOME_NEARBY_SHOP -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_nearby_shop, parent, false)
                return ShopListViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_nearby_shop, parent, false)
                return InfomationListViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.adapterPosition) {
            HomeAdapter.TYPE_HOME_GOLD_PRICE -> {
                datas.forEach {
                    if (it is GoldPriceBean) {
                        (holder as GoldPriceViewHolder).buildGoldPrice(it)
                    }
                }

            }
            HomeAdapter.TYPE_HOME_ORDER_LIST -> {
                datas.forEach {
                    if (it is UserOderBean) {
                        (holder as OrderMsgViewHolder).buildOrderMsg(it)
                    }
                }
            }
            HomeAdapter.TYPE_HOME_TOTAL_RECYCLE -> {
                datas.forEach {
                    if (it is RecyclerGoldBean) {
                        (holder as TotalRecyclerGoldViewHolder).buildRecyclerGold(it)
                    }
                }
            }

            HomeAdapter.TYPE_HOME_NEARBY_SHOP -> {
                datas.forEach {
                    if (it is NearbyShopBean) {
                        (holder as ShopListViewHolder).buildShopList(it)
                    }
                }
            }
            else -> {
                datas.forEach {
                    if (it is InformationBean) {
                        (holder as InfomationListViewHolder).buildInfomationList(it)
                    }
                }
            }
        }
    }

    inner class GoldPriceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            addEvent()
            itemView.mTvGoldTrend.setOnClickListener {

            }
            itemView.mTvRecycleGold.paint.isFakeBoldText = true

        }

        fun buildGoldPrice(contentBean: GoldPriceBean?) {
            val priceText = "${contentBean?.content?.goldPrice} 元/克"
            itemView.mTvGoldPrice.text = SpannableString(priceText).apply {
                setSpan(RelativeSizeSpan(3.33f), 0, priceText.length - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(StyleSpan(Typeface.BOLD), 0, priceText.length - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }

        @SuppressLint("CheckResult")
        private fun addEvent() {
            RxBus.getDefault().toObservable(RxEvent::class.java)
                .subscribe { t ->
                    if (t?.eventType == EventConstant.NOTIF_GOLD_PRICE) {
                        t.`object` as GoldPriceBean
                        buildGoldPrice(t.`object` as GoldPriceBean)
                    }
                }
        }
    }

    inner class OrderMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun buildOrderMsg(userOderBean: UserOderBean) {
            //我要卖金的点击事件
            itemView.mTvBuyGold.setOnClickListener {

            }
            val flipperViews = ArrayList<View>()
            for (i in 0 until userOderBean.content.size) {
                val contentBean = userOderBean.content[i]
                val view = LayoutInflater.from(itemView.context).inflate(R.layout.layout_order_text, null, false)
                var text = "${contentBean.createTime},${contentBean.username}的黄金估计${contentBean.amount}元"
                view.mTvOrderMsg.text = SpannableStringBuilder(text).apply {
                    setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.color_EDA835)),
                        text.length - (contentBean.amount.toString().length + 1),
                        text.length - 1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }

                flipperViews.add(view)
            }
            itemView.pushUpFlipper.apply {
                removeAllViews()
                flipperViews.forEach {
                    addView(it)
                }

                startFlipping()
                setFlipInterval(2000)
                inAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.scroll_in)
                outAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.scroll_out)
            }


        }
    }

    inner class TotalRecyclerGoldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun buildRecyclerGold(recyclerGoldBean: RecyclerGoldBean) {
            val d: Int = (recyclerGoldBean.content.weight / (1000 * 1000)).toInt()
            val kg: Int = ((recyclerGoldBean.content.weight - (d * 1000 * 1000)) / 1000).toInt()
            val g: Int = (recyclerGoldBean.content.weight - d * 1000 * 1000 - kg * 1000).toInt()
            if (d > 0) {
                itemView.mTvTotalTon.text = d.toString()
            } else {
                itemView.mTvTotalTon.visibility = View.GONE
            }
            if (kg > 0) {
                itemView.mTvTotalKg.text = kg.toString()
            } else {
                itemView.mTvTotalKg.visibility = View.GONE
            }
            if (g > 0) {
                itemView.mTvTotalK.text = g.toString()
            } else {
                itemView.mTvTotalK.visibility = View.GONE
            }

        }
    }

    inner class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun buildShopList(nearbyShopBean: NearbyShopBean) {
            itemView.mTvTitle.apply {
                text = "附近店铺"
                paint.isFakeBoldText = true
            }
            itemView.mTvInfo.text = "前往附近店铺进行黄金回收"
            itemView.setOnClickListener { }
            itemView.mRecyclerView.apply {
                layoutManager = GridLayoutManager(itemView.context, 3)
                adapter = HomeNearbyShopAdapter(
                    itemView.context,
                    nearbyShopBean.content as ArrayList<NearbyShopBean.ContentBean>
                )
            }
        }
    }

    inner class InfomationListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun buildInfomationList(informationBean: InformationBean) {
            itemView.mTvTitle.apply {
                text = "黄金资讯"
                paint.isFakeBoldText = true
            }
            itemView.mTvInfo.text = "为您提供最新 有价值的黄金领域资讯"
            itemView.setOnClickListener { }
            itemView.mRecyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = HomeInformationAdapter(
                    itemView.context,
                    informationBean.content.list as ArrayList<InformationBean.ContentBean.ListBean>
                )
            }
        }
    }
}