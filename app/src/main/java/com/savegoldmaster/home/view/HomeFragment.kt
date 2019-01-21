package com.savegoldmaster.home.view

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.HomePresenterImpl
import com.savegoldmaster.home.view.adapter.HomeAdapter
import com.savegoldmaster.utils.*
import com.savegoldmaster.utils.glide.GlideImageView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

class HomeFragment : BaseMVPFragment<HomePresenterImpl>(), HomeContract.HomeView, View.OnClickListener {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var listBean: ArrayList<Object> = ArrayList()
    private var homePresenterImpl: HomePresenterImpl? = null
    private var homeAdapter: HomeAdapter? = null
    private var start: CountDownTimer? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View?) {
        mImageMsg.setOnClickListener(this)
        mImageClose.setOnClickListener(this)
        listBean = ArrayList()
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter(listBean!!)
        mRecyclerView.adapter = homeAdapter
        mRecyclerView.addFooterView(
            layoutInflater.inflate(
                R.layout.layout_home_company_info, mRecyclerView.parent as ViewGroup, false
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (px2dp(context!!, scrollY) > 48) {
                    mLayoutTopTab.visibility = View.VISIBLE
                } else {
                    mLayoutTopTab.visibility = View.GONE
                }
            }
        }
        initData()
    }

    private fun initData() {
        listBean.add(GoldPriceBean() as Object)
        homePresenterImpl?.getBanner(1, 5, 2)
        homePresenterImpl?.getNotice()
        homePresenterImpl?.getGoldPrice()
        homePresenterImpl?.getGoldNewOder()
        homePresenterImpl?.getRecycleGold()
        val location = LocationUtils.getInstance(context).showLocation()
        if (location != null) {
            homePresenterImpl?.getNearbyShop(location.latitude.toString(), location.longitude.toString())
        } else {
            homePresenterImpl?.getNearbyShop("", "")
        }
        homePresenterImpl?.getNewInformation()
//        homePresenterImpl?.getMessageTips()
    }

    override fun createPresenter(): HomePresenterImpl {
        homePresenterImpl = HomePresenterImpl()
        return homePresenterImpl as HomePresenterImpl
    }

    override fun onClick(v: View?) {
        when (v) {
            mImageMsg -> {
            }
            mImageClose -> {
                mLayoutNotice.visibility = View.GONE
            }
        }

    }

    override fun getBannerData(bean: BannerBean) {
        buildBanner(bean)
    }

    override fun getNotice(noticeBean: NoticeBean) {
        if (noticeBean.content != null && ListUtil.isNotEmpty(noticeBean.content.list)) {
            buildNotice(noticeBean.content.list[0])
        } else {
            mLayoutNotice.visibility = View.GONE
        }
    }

    override fun getGoldPrice(goldPriceBean: GoldPriceBean) {
        for(i in 0 until listBean.size){
            if (listBean[i] is GoldPriceBean){
                listBean[i] = goldPriceBean as Object
            }
        }
        homeAdapter?.notifyItemChanged(HomeAdapter.TYPE_HOME_GOLD_PRICE)
        countDownTime()
    }

    override fun getGoldNewOder(userOderBean: UserOderBean) {
        listBean.add(buildOrderTime(userOderBean) as Object)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getRecycleGold(recyclerGoldBean: RecyclerGoldBean) {
        listBean.add(recyclerGoldBean as Object)
        homeAdapter?.notifyDataSetChanged()

    }


    override fun getNearbyShop(nearbyShopBean: NearbyShopBean) {
        listBean.add(nearbyShopBean as Object)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getNewInformation(informationBean: InformationBean) {
        listBean.add(informationBean as Object)
        homeAdapter?.notifyDataSetChanged()
    }

    fun buildBanner(bannerBean: BannerBean) {
        val views = mutableListOf<GlideImageView>()
        val imageUrls = mutableListOf<XBannerBean>()
        for (i in 0 until bannerBean.content.size) {
            val mImageView = GlideImageView(context)
            mImageView.setDefaultImage(R.mipmap.ic_launcher)
            mImageView.scaleType = ImageView.ScaleType.FIT_XY
            mImageView.setImage(bannerBean.content[i].imgUrl)
            imageUrls.add(XBannerBean(bannerBean.content[i].imgUrl))
            views.add(mImageView)
        }

        banner.apply {
            setBannerData(imageUrls)
            loadImage { banner, model, view, position ->
                Glide.with(context).load((model as XBannerBean).imagerUrls)
                    .into(view as ImageView)
            }
            setOnItemClickListener { banner, model, view, position ->
                if (StringUtil.isNotEmpty(bannerBean.content[position].hrefUrl)) {
                    OutWebActivity.start(context, bannerBean.content[position].hrefUrl)
                }
            }
        }

    }

    private fun countDownTime() {
        start = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                homePresenterImpl?.getGoldPrice()
            }
        }.start()
    }

    fun buildNotice(noticeBean: NoticeBean.ContentBean.ListBean) {
        mTvNotice.apply {
            text = noticeBean.content
            isSelected = true
        }
    }

    fun buildOrderTime(userOderBean: UserOderBean): UserOderBean {
        for (i in 0 until userOderBean.content.size) {
            userOderBean.content[i].createTime =
                    DateTimeUtil.getTime(DateTimeUtil.dateToStamp(userOderBean.content[i].createTime))
        }
        return userOderBean
    }

    override fun onDestroyView() {
        super.onDestroyView()
        start = null
    }

    //将px转换为dp
    fun px2dp(context: Context, pxValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}