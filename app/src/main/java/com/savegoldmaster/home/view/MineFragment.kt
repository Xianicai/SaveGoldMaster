package com.savegoldmaster.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.net.Uri
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.AdapterView
import com.example.zhanglibin.savegoldmaster.R
import com.savegoldmaster.account.LoginActivity
import com.savegoldmaster.base.view.BaseMVPFragment
import com.savegoldmaster.home.model.bean.UserBean
import com.savegoldmaster.home.presenter.Contract.UserContract
import com.savegoldmaster.home.presenter.UserPresenterImpl
import com.savegoldmaster.home.view.adapter.MineListAdapter
import com.savegoldmaster.home.view.adapter.MineListBean
import com.savegoldmaster.utils.LocationUtils
import com.savegoldmaster.utils.SharedPreferencesHelper
import com.savegoldmaster.utils.ToastUtil
import com.savegoldmaster.utils.adapter.OnItemClickListener
import com.savegoldmaster.utils.rxbus.EventConstant
import com.savegoldmaster.utils.rxbus.RxBus
import com.savegoldmaster.utils.rxbus.RxEvent
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseMVPFragment<UserPresenterImpl>(), UserContract.UserView, View.OnClickListener {
    companion object {
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    private val mTitles = arrayOf("福利券", "交易记录", "银行卡管理", "地址管理", "常见问题", "在线客服", "关于我们")
    private val mImages = arrayOf(
        R.mipmap.ic_mine_coupon, R.mipmap.ic_mine_deal_order, R.mipmap.ic_mine_bank_card,
        R.mipmap.ic_mine_address, R.mipmap.ic_mine_questions, R.mipmap.ic_mine_service, R.mipmap.ic_mine_about
    )

    private var userPresenterImpl: UserPresenterImpl? = null
    private var token: String = ""
    private var userId: String = ""
    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View?) {
        mButtonLogin.setOnClickListener(this)
        mTvServiceTel.setOnClickListener(this)
        mViewOrder.setOnClickListener(this)
        initRecyclerView()
        initData()
        addEvent()

    }

    private fun initRecyclerView() {
        mLayoutRecycleOrder.setOnClickListener(this)
        var listBean: MutableList<MineListBean> = arrayListOf()
        for (i in 0 until mTitles.size) {
            listBean.add(MineListBean(mTitles[i], mImages[i], mImages[i]))
        }
        mRecyclerView.layoutManager = GridLayoutManager(context, 4)
        mRecyclerView.adapter = MineListAdapter(context!!, listBean as ArrayList<MineListBean>).apply {
            object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    when (position) {
                        0 -> {
                        }
                        1 -> {
                        }
                        2 -> {
                        }
                        3 -> {
                        }
                        4 -> {
                        }
                        5 -> {
                        }
                        6 -> {
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        SharedPreferencesHelper(context, "UserBean").run {
            token = getSharedPreference("token", "").toString().trim()
            userId = getSharedPreference("userId", "").toString().trim()
        }
        if (userId.isNotEmpty() && token.isNotEmpty()) {
            userPresenterImpl?.getUserDetail()
        }

    }

    @SuppressLint("CheckResult")
    private fun addEvent() {
        RxBus.getDefault().toObservable(RxEvent::class.java)
            .subscribe { t ->
                if (t?.eventType == EventConstant.GET_USER_DETAIL) {
                    initData()
                }
            }
    }


    override fun createPresenter(): UserPresenterImpl {
        userPresenterImpl = UserPresenterImpl()
        return userPresenterImpl as UserPresenterImpl

    }

    override fun onClick(v: View?) {
        when (v) {
            mButtonLogin -> {
                LoginActivity.start(activity!!)
//                val location = LocationUtils.getInstance(context).showLocation()
//                if (location != null) {
//                    ToastUtil.showMessage("纬度：" + location.latitude + "经度：" + location.longitude)
//                }
            }
            mTvServiceTel -> {

                startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:" + 4008196199)
                    ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
            mLayoutRecycleOrder, mViewOrder -> {
                //我的订单
            }


        }
    }

    override fun getUserDetail(userBean: UserBean) {
        mLayoutLogin.visibility = View.GONE
        mLayoutUserView.visibility = View.VISIBLE
//        mImageHead.setImage(userBean.content.)
        mTvName.text = userBean.content.userName
        mTvPhoneNum.text = userBean.content.telephone
    }
}