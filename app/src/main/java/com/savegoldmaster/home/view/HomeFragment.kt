package com.savegoldmaster.home.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.model.bean.*
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.HomePresenterImpl
import com.savegoldmaster.home.presenter.UserPresenterImpl
import com.savegoldmaster.home.view.adapter.HomeAdapter
import com.savegoldmaster.utils.LocationUtils
import com.savegoldmaster.utils.ToastUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
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
        listBean = ArrayList()
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        homeAdapter = HomeAdapter(this.context!!, listBean!!)
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
        homePresenterImpl?.getNearbyShop("39.90", "116.39")
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
        }

    }

    override fun getBannerData(bean: BannerBean) {
        listBean?.add(bean as Object)
        homeAdapter?.notifyDataSetChanged()


    }

    override fun getNotice(noticeBean: NoticeBean) {
        listBean?.add(noticeBean.content.list[0] as Object)
        homeAdapter?.notifyDataSetChanged()
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
}