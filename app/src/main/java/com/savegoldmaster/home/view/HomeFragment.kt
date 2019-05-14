package com.savegoldmaster.home.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.CountDownTimer
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.savegoldmaster.R
import com.savegoldmaster.account.LoginActivity
import com.savegoldmaster.account.UserUtil
import com.savegoldmaster.base.BaseApplication
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.common.WebUrls
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.HomePresenterImpl
import com.savegoldmaster.home.view.adapter.HomeAdapter
import com.savegoldmaster.utils.DateTimeUtil
import com.savegoldmaster.utils.LocationUtils
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.StringUtil
import com.savegoldmaster.utils.glide.GlideImageView
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import com.savegoldmaster.utils.view.PushDialog
import com.savegoldmaster.utils.webutil.OutWebActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_home_company_info.*
import kotlinx.android.synthetic.main.layout_home_company_info.view.*


class HomeFragment : BaseMVPFragment<HomePresenterImpl>(), HomeContract.HomeView, View.OnClickListener {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var listBean: ArrayList<Any> = ArrayList()
    private var homePresenterImpl: HomePresenterImpl? = null
    private var homeAdapter: HomeAdapter? = null
    private var start: CountDownTimer? = null
    private var noticeBean: NoticeBean? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View?) {

//        val channelName = ChannelUtil.getChannelName(activity)
//        val channel = WalleChannelReader.getChannel(BaseApplication.instance.applicationContext)
//        ToastUtil.showMessage("应用市场信息" + channelName)

        addEvent()
        //获取推送活动弹窗
        if (UserUtil.isLogin()) {
            homePresenterImpl?.getNotice()
        }
        mImageMsg.setOnClickListener(this)
        mImageClose.setOnClickListener(this)
        mImageMsgV2.setOnClickListener(this)
        mLayoutNotice.setOnClickListener(this)
        listBean = ArrayList()
        mRecyclerView.isNestedScrollingEnabled = false
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter(listBean)
        mRecyclerView.adapter = homeAdapter
        val bottomView = layoutInflater.inflate(
            R.layout.layout_home_company_info, mRecyclerView.parent as ViewGroup, false
        )
        mRecyclerView.addFooterView(
            bottomView
        )
        (bottomView.mLayoutKnowOur).setOnClickListener(this)
        (bottomView.mLayoutSafety).setOnClickListener(this)
        mScrollView.setOnScrollListener { scrollY ->
            if (px2dp(activity!!, scrollY) > 48) {
                mLayoutTopTab.visibility = View.VISIBLE
            } else {
                mLayoutTopTab.visibility = View.GONE
            }
        }
        //获取首页站内推送
        homePresenterImpl?.getPushNotice(2, 1, 2)
        initData()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            if (UserUtil.isLogin()) {
                homePresenterImpl?.getNotice()
            }
        }
    }

    private fun initData() {
        listBean.clear()
        homeAdapter?.notifyDataSetChanged()
        listBean.add(GoldPriceBean() as Any)
        //获取轮播图
        homePresenterImpl?.getBanner(1, 5, 2)
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
    }

    override fun createPresenter(): HomePresenterImpl {
        homePresenterImpl = HomePresenterImpl()
        return homePresenterImpl as HomePresenterImpl
    }

    override fun onClick(v: View?) {
        when (v) {
            mImageClose -> {
                mLayoutNotice.visibility = View.GONE
            }
            mImageMsgV2, mImageMsg -> {
                if (UserUtil.isLogin()) {
//                    (activity as MainActivity).isReadMsg = true
                    mImageUnreadV2.visibility = View.GONE
                    mImageUnread.visibility = View.GONE
                    OutWebActivity.start(context!!, WebUrls.MESSAGE_LIST)
                } else {
                    LoginActivity.start(context!!, "")
                }
            }
            mLayoutNotice -> {
                OutWebActivity.start(context!!, WebUrls.MESSAGE_DETAIL + noticeBean?.content?.id)
            }
            mLayoutKnowOur -> {
                OutWebActivity.start(context!!, WebUrls.UNDERSTAND)

            }
            mLayoutSafety -> {
                OutWebActivity.start(context!!, WebUrls.SECURITY)
            }
        }

    }

    override fun getBannerData(bean: BannerBean) {
        buildBanner(bean)
    }

    override fun getNotice(noticeBean: NoticeBean) {
        this.noticeBean = noticeBean
//        if ((activity as MainActivity).isReadMsg) {
//            mImageUnreadV2.visibility = View.GONE
//            mImageUnread.visibility = View.GONE
//            return
//        }
        if (StringUtil.isNotEmpty(noticeBean.content.title)) {
            mLayoutNotice.visibility = View.VISIBLE
            buildNotice(noticeBean.content)
            Glide.with(context).load(R.mipmap.ic_home_notice).into(mImageNotice)
        } else {
            mLayoutNotice.visibility = View.GONE
        }

        if (noticeBean.content.count > 0) {
            mImageUnreadV2.visibility = View.VISIBLE
            mImageUnread.visibility = View.VISIBLE
            RxBus.getDefault().post(RxEvent(EventConstant.MINE_FRAGMENT_MSG, noticeBean.content.count))
        } else {
            mImageUnreadV2.visibility = View.GONE
            mImageUnread.visibility = View.GONE
        }
    }

    override fun getGoldPrice(goldPriceBean: GoldPriceBean) {
        for (i in 0 until listBean.size) {
            if (listBean[i] is GoldPriceBean) {
                listBean[i] = goldPriceBean as Any
            }
        }
        RxBus.getDefault().post(RxEvent(EventConstant.NOTIF_GOLD_PRICE, goldPriceBean))
        countDownTime()
    }

    override fun getGoldNewOder(userOderBean: UserOderBean) {
        listBean.add(buildOrderTime(userOderBean) as Any)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getRecycleGold(recyclerGoldBean: RecyclerGoldBean) {
        listBean.add(recyclerGoldBean as Any)
        homeAdapter?.notifyDataSetChanged()

    }


    override fun getNearbyShop(nearbyShopBean: NearbyShopBean) {
        listBean.add(nearbyShopBean as Any)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getNewInformation(informationBean: InformationBean) {
        listBean.add(informationBean as Any)
        homeAdapter?.notifyDataSetChanged()
    }

    fun buildBanner(bannerBean: BannerBean) {
        val views = mutableListOf<GlideImageView>()
        val imageUrls = mutableListOf<XBannerBean>()
        var fromUrl = if (WebUrls.BASE_URL == "https://app.au32.cn/") "app.au32.cn" else "app-test.au32.cn"
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
                    .placeholder(R.mipmap.ic_banner_def)
                    .into(view as ImageView)
            }
            setOnItemClickListener { banner, model, view, position ->
                if (StringUtil.isNotEmpty(bannerBean.content[position].hrefUrl)) {
                    val preferencesHelper = SharedPreferencesHelper(BaseApplication.instance, "UserBean")
                    val token = preferencesHelper.getSharedPreference("token", "").toString().trim()
                    val userId = preferencesHelper.getSharedPreference("userId", "").toString().trim()
                    var urlToken =
                        bannerBean.content[position].hrefUrl + "?fromUrl=" + fromUrl + "&token="
                    if (UserUtil.isLogin()) {
                        urlToken =
                            bannerBean.content[position].hrefUrl + "?fromUrl=" + fromUrl + "&token=" + userId + "_" + token
                    }
                    OutWebActivity.start(
                        context,
                        urlToken
                    )
                }
            }
        }

    }

    override fun getPushNotice(bean: BannerBean) {
        if (bean.content.isNotEmpty() && bean.content.size > 0 && bean.content[0]?.imgUrl?.isNotEmpty() == true) {
            PushDialog(activity).setDate(bean).show()
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

    fun buildNotice(noticeBean: NoticeBean.ContentBean) {
        mTvNotice.apply {
            text = noticeBean.title
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
    fun px2dp(context: Activity, pxValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    @SuppressLint("CheckResult")
    private fun addEvent() {
        RxBus.getDefault().toObservable(RxEvent::class.java)
            .subscribe { t ->
                when {
                    t?.eventType == EventConstant.USER_LOGIN -> {
                        (activity as Activity).runOnUiThread { initData() }
                    }
                    t?.eventType == EventConstant.OUT_LOGIN -> (context as Activity).runOnUiThread {
                        mLayoutNotice.visibility = View.GONE
                        mImageUnreadV2.visibility = View.GONE
                        mImageUnread.visibility = View.GONE
                    }
                    t?.eventType == EventConstant.REFRESH_MSG -> {
                        if (UserUtil.isLogin()) {
                            homePresenterImpl?.getNotice()
                        }
                    }
                }
            }
    }

}
