package com.savegoldmaster.home.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.model.bean.BannerBean
import com.savegoldmaster.home.presenter.Contract.HomeContract
import com.savegoldmaster.home.presenter.HomePresenterImpl
import com.savegoldmaster.home.presenter.UserPresenterImpl
import com.savegoldmaster.home.view.adapter.HomeAdapter
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
        initData()
    }

    private fun initData() {
        homePresenterImpl?.getBanner(1, 5, 2)
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
        listBean?.add(0, bean as Object)
        homeAdapter?.notifyItemChanged(1)

    }
}