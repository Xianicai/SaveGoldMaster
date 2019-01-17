package com.savegoldmaster.home.view

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.HomePresenterImpl
import com.savegoldmaster.home.view.adapter.HomeAdapter
import com.savegoldmaster.utils.ListUtil
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.glide.GlideImageView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

class HomeFragment : BaseMVPFragment<HomePresenterImpl>(), HomeContract.HomeView, View.OnClickListener {


    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private var listBean: ArrayList<Object>? = null
    private var homePresenterImpl: HomePresenterImpl? = null
    private var homeAdapter: HomeAdapter? = null
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
        initData()
    }

    private fun initData() {
        homePresenterImpl?.getBanner(1, 5, 2)
        homePresenterImpl?.getNotice(1, 10, 1)
        homePresenterImpl?.getGoldPrice()
        homePresenterImpl?.getGoldNewOder()
        homePresenterImpl?.getRecycleGold()
//        val location = LocationUtils.getInstance(context).showLocation()
//        if (location != null) {
//            ToastUtil.showMessage("纬度：" + location.latitude + "经度：" + location.longitude)
//        }
        homePresenterImpl?.getNearbyShop("","")
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
        }else{
            mLayoutNotice.visibility = View.GONE
        }
    }

    override fun getGoldPrice(goldPriceBean: GoldPriceBean) {
        listBean?.add(goldPriceBean.content as Object)
        homeAdapter?.notifyDataSetChanged()
    }

    override fun getGoldNewOder(userOderBean: UserOderBean) {
        listBean?.add(userOderBean as Object)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getRecycleGold(recyclerGoldBean: RecyclerGoldBean) {
        listBean?.add(recyclerGoldBean as Object)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getMessageTips() {
    }

    override fun getNearbyShop(nearbyShopBean: NearbyShopBean) {
        listBean?.add(nearbyShopBean as Object)
        homeAdapter?.notifyDataSetChanged()

    }

    override fun getNewInformation(informationBean: InformationBean) {
        listBean?.add(informationBean as Object)
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
                ToastUtil.showMessage("点击了第" + position + "图片")
            }
        }

    }

    fun buildNotice(noticeBean: NoticeBean.ContentBean.ListBean) {
        mTvNotice.apply {
            text = noticeBean.content
            isSelected = true
        }
    }
}